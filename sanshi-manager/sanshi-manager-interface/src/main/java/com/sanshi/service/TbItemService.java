package com.sanshi.service;

import java.util.List;

import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.pojo.TbItemFreemarker;
import com.sanshi.result.EasyUIDataGtidResult;
import com.sanshi.result.TaotaoResult;

public interface TbItemService {

    /**
     * 分页查询
     */
    public EasyUIDataGtidResult findAllTbItem(int page, int rows);

    /**
     * 添加商品
     */
    public TaotaoResult addTbItem(TbItem item, TbItemDesc TbItemParamItem, String itemParams);

    /**
     * 根据id修改商品状态
     */
    public void updateItemStatus(int status, List<Long> id);

    /**
     * 根据商品itemId查询商品
     *
     * @param itemId
     * @return
     */
    TbItem getTbItemById(long itemId);

    /**
     * 根据itemId查询商品详情
     *
     * @param itemId
     * @return
     */
    TbItemDesc getTbItemDescById(long itemId);

    /**
     * 根据商品id查询商品的规格参数
     *
     * @param itemId
     * @return TaotaoResult
     */
    String getTbItemParamItemByid(long itemId) throws Exception;

    /**
     * 绑定商品id和模板信息到数据看
     *
     * @param tbItemFreemarker
     */
    void addTbItemFreemarker(TbItemFreemarker tbItemFreemarker);

    /**
     * 根据商品id查询是否有模板
     *
     * @param itemId 商品id
     * @return
     */
    TbItemFreemarker getTbItemFreemarkerById(long itemId);

}
