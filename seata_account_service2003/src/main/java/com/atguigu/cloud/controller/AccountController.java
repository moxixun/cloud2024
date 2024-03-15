package com.atguigu.cloud.controller;

import com.atguigu.cloud.response.ResultData;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("/account/decrease")
    public ResultData decrease(@RequestParam("userId") Long userId
            , @RequestParam("money") Long money){

        accountService.decrease(userId, money);
        return ResultData.success("账户扣减成功");
    }

}
