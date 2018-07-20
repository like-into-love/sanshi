package com.sanshi.mapper;


import com.sanshi.pojo.TbOrderItem;

public interface TbOrderItemMapper {
    /**
     * 插入订单中的每个商品
     *
     * @param tbOrderItem
     */
    void insertTbOrderItem(TbOrderItem tbOrderItem);
}