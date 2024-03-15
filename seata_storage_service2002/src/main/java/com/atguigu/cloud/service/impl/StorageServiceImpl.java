package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.mapper.StorageMapper;
import com.atguigu.cloud.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageMapper storageMapper;
    /**
     * 更新库存信息
     * @param productId
     * @param count
     */
    public void decrease(Long productId, Integer count) {
        log.info("--------------开始扣减库存");
        storageMapper.decrease(productId, count);
        log.info("--------------扣减库存成功");
    }
}
