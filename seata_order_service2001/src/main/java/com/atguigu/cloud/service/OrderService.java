package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Order;

public interface OrderService {

    /**
     * 用户下单
     */
    void createOrder(Order order);

}
