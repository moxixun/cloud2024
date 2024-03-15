package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.response.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/feign/pay/add")
    public ResultData add(@RequestBody PayDTO payDTO){

        ResultData resultData = payFeignApi.add(payDTO);

        return resultData;
    }

    @DeleteMapping("/feign/pay/del/{id}")
    public ResultData delete(@PathVariable("id") Integer id){

        ResultData resultData = payFeignApi.deleteById(id);

        return resultData;
    }

    @PutMapping("/feign/pay/update")
    public ResultData update(@RequestBody PayDTO payDTO){

        ResultData resultData = payFeignApi.update(payDTO);

        return resultData;
    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id){

        ResultData resultData = null;

        try {
            System.out.println("开始运行..." + DateUtil.now());
            resultData = payFeignApi.getById(id);
        } catch (Exception e) {
            System.out.println("超时异常..." + DateUtil.now());
            throw new RuntimeException(e);
        }

        return resultData;
    }

    //验证feign客户端负载均衡
    @GetMapping("/feign/feignBalance")
    public String feignBalance(){

        String info = payFeignApi.getInfo();

        return info;
    }

}
