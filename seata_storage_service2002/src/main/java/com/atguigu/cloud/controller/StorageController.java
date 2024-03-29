package com.atguigu.cloud.controller;

import com.atguigu.cloud.response.ResultData;
import com.atguigu.cloud.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StorageController {

    @Resource
    private StorageService storageService;
    /**
     * 用户下单后,减少库存
     * @return
     */
    @RequestMapping("/storage/decrease")
    public ResultData decrease(Long productId, Integer count){

        storageService.decrease(productId, count);

        return ResultData.success("库存更新成功");
    }
}
