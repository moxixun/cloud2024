package com.atguigu.cloud.service;

import org.apache.ibatis.annotations.Param;

public interface AccountService {

    /**
     * 扣减用户账户余额
     * @param userId
     * @param money
     */
    void decrease(@Param("userId") Long userId, @Param("money") Long money);

}
