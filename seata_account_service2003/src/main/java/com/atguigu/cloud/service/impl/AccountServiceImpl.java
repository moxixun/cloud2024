package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.mapper.AccountMapper;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;
    /**
     * 扣减用户账户余额
     * @param userId
     * @param money
     */

    public void decrease(Long userId, Long money) {

        log.info("-------------开始扣减账户余额");
        accountMapper.decrease(userId, money);

        //故意制造异常和超时
        int a = 10/0;
        //timeOut();

        log.info("-------------扣减账户余额成功");
    }

    private void timeOut() {
        try {
            Thread.sleep(65000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
