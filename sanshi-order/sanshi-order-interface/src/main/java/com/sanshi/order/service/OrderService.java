package com.sanshi.order.service;

import com.sanshi.order.pojo.Order;
import com.sanshi.result.TaotaoResult;

public interface OrderService {

    TaotaoResult createOrder(Order order);

}
