package com.sanshi.order.pojo;

import com.sanshi.pojo.TbOrder;
import com.sanshi.pojo.TbOrderItem;
import com.sanshi.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

public class Order extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderItems=" + orderItems +
                ", orderShipping=" + orderShipping +
                '}';
    }
}
