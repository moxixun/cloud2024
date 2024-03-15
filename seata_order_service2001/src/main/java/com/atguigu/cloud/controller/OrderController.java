package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.response.ResultData;
import com.atguigu.cloud.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping("/order/create")
    public ResultData createOrder(Order order){

        orderService.createOrder(order);

        return ResultData.success(order);
    }
}
