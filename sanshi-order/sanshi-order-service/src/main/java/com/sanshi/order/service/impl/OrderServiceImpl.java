package com.sanshi.order.service.impl;

import com.sanshi.mapper.TbOrderItemMapper;
import com.sanshi.mapper.TbOrderMapper;
import com.sanshi.mapper.TbOrderShippingMapper;
import com.sanshi.order.jedis.JedisClient;
import com.sanshi.order.pojo.Order;
import com.sanshi.order.service.OrderService;
import com.sanshi.order.utils.BuyerRate;
import com.sanshi.order.utils.OrderState;
import com.sanshi.pojo.TbOrderItem;
import com.sanshi.pojo.TbOrderShipping;
import com.sanshi.result.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    JedisClient jedisClient;
    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;
    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Override
    public TaotaoResult createOrder(Order order) {
        //判断这个key是否存在，不存在创建这个key,存在就自增加一
        if (!jedisClient.exists("ORDER_GEN_KEY")) {
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        //对订单号自增
        Long orderId = jedisClient.incr(ORDER_GEN_KEY);
        //设置订单号，补全其他信息
        order.setOrderId(String.valueOf(orderId));
        //订单状态
        order.setStatus(OrderState.NOT_PAID.getStatus());
        //是否评论的状态
        order.setBuyerRate(BuyerRate.NOT_RATED.getStatus());
        //订单生成的时间
        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        //插入数据库
        try {
            tbOrderMapper.insertOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "插入订单信息失败");
        }
        //取到订单里面商品遍历并插入到tb_order_item订单详情数据库
        List<TbOrderItem> orderItems = order.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            //可以不要判断redis里面是否有这个key没有会自动创建一个这个key并赋初值为1
            Long id = jedisClient.incr("ORDER_DETAIL_GEN_KEY");
            tbOrderItem.setId(String.valueOf(id));
            //赋值OrderId
            tbOrderItem.setOrderId(order.getOrderId());
            try {
                tbOrderItemMapper.insertTbOrderItem(tbOrderItem);

            } catch (Exception e) {
                e.printStackTrace();
                return TaotaoResult.build(500, "插入订单详情信息失败");
            }
        }
        //拿到 TbOrderShipping商品对象
        TbOrderShipping orderShipping = order.getOrderShipping();
        //补全信息插入订单物流表
        orderShipping.setOrderId(order.getOrderId());
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        try {
            tbOrderShippingMapper.insertTbOrderShipping(orderShipping);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "插入订单物流信息失败");
        }
        //返回订单号
        return TaotaoResult.ok(orderId);
    }
}
