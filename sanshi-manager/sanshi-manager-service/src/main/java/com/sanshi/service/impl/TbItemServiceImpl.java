package com.sanshi.service.impl;

import java.util.Date;
import java.util.List;

import com.sanshi.jedis.JedisClient;
import com.sanshi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanshi.mapper.TbItemDescMapper;
import com.sanshi.mapper.TbItemMapper;
import com.sanshi.mapper.TbItemParamItemMapper;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.pojo.TbItemParamItem;
import com.sanshi.result.EasyUIDataGtidResult;
import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemService;
import com.sanshi.utils.IDUtils;

import javax.jms.*;

//要与dubbo配置接口实现的ref的名字一样 ，如果不写("tbItemServiceImpl")默认的首字母小写tbItemServiceImpl
@Service("tbItemServiceImpl")
public class TbItemServiceImpl implements TbItemService {
    @Value("${ITEM_INF}")
    private String ITEM_INF;
    @Value("${BASE}")
    private String BASE;
    @Value("${DESC}")
    private String DESC;
/*    @Value("${PARAM}")
    private String PARAM;*/

    @Autowired
    TbItemMapper tbItemMapper;

    @Autowired
    TbItemDescMapper tbItemDescMapper;

    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    @Autowired
    JmsTemplate jmsTemplate;
    // 与bean id一样
    @Autowired
    Destination queue;

    @Autowired
    JedisClient jedisClient;

    @Override
    public EasyUIDataGtidResult findAllTbItem(int page, int rows) {
        //// 获取第page页，显示rows条内容，
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.findAllTbItem();
        PageInfo<TbItem> p = new PageInfo<TbItem>(list);
        EasyUIDataGtidResult result = new EasyUIDataGtidResult();
        result.setPage(p.getTotal());
        result.setRows(list);
        return result;
    }

    /**
     * 添加商品
     *
     * @return
     */
    @Override
    public TaotaoResult addTbItem(TbItem item, TbItemDesc itemDesc, String itemParams) {
        // 生成商品id，使用时间+随机数策略生成
        Long itemId = IDUtils.genItemId();
        try {
            // 补全商品信息
            // 与tb_item_desc表建立逻辑主外键
            item.setId(itemId);
            // 商品状态，1-正常，2-下架，3-删除
            item.setStatus((byte) 1);
            Date date = new Date();
            item.setCreated(date);
            item.setUpdated(date);
            // 把数据插入到商品表
            tbItemMapper.insertTbItem(item);

            // 补全商品描述信息
            itemDesc.setItemId(itemId);
            itemDesc.setCreated(date);
            itemDesc.setUpdated(date);
            // 把数据插入到商品描述表
            tbItemDescMapper.insertTbItemDesc(itemDesc);

            // 补全商品规格
            TbItemParamItem itemParamItem = new TbItemParamItem();
            itemParamItem.setItemId(itemId);
            itemParamItem.setParamData(itemParams);
            itemParamItem.setCreated(date);
            itemParamItem.setUpdated(date);
            // 把数据添加到商品规格表
            tbItemParamItemMapper.addTbItemParamItem(itemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "添加商品 出错");
        }
        // 发送消息队列
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(itemId + "");
                return message;
            }
        });
        return TaotaoResult.resultStatus(200);

    }

    @Override
    /**
     * 修改商品状态
     */
    public void updateItemStatus(int status, List<Long> id) {
        tbItemMapper.updateItemStatus(status, id);
    }

    //根据商品itemId查询商品
    @Override
    public TbItem getTbItemById(long itemId) {
        //取缓存
        try {
            String json = jedisClient.get(ITEM_INF + ":" + itemId + "" + ":" + BASE);
            System.out.println("商品基本缓存读取成功");
            if (StringUtils.isNotBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItem tbItem = tbItemMapper.getTbItemById(itemId);
        //存缓存
        //ITEM_IFO:123456:BASE,一种格式
        try {
            jedisClient.set(ITEM_INF + ":" + itemId + "" + ":" + BASE, JsonUtils.objectToJson(tbItem));
            System.out.println("商品基本缓存加入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public TbItemDesc getTbItemDescById(long itemId) {
        //取缓存
        try {
            String json = jedisClient.get(ITEM_INF + ":" + itemId + "" + ":" + DESC);
            System.out.println("商品详情缓存读取成功");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.getTbItemDescById(itemId);
        //存缓存
        try {
            jedisClient.set(ITEM_INF + ":" + itemId + "" + ":" + DESC, JsonUtils.objectToJson(tbItemDesc));
            System.out.println("商品详情缓存加入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }

}
