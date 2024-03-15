package com.atguigu.cloud.service;

public interface StorageService {

    /**
     * 更新库存信息
     * @param productId
     * @param count
     */
    void decrease(Long productId, Integer count);

}
