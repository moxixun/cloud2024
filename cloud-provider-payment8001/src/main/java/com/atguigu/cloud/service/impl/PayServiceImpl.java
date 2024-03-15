package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.mapper.PayMapper;
import com.atguigu.cloud.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private PayMapper payMapper;

    @Override
    public String add(Pay pay) {

        int i = payMapper.insertSelective(pay);

        return "成功的插入" + i + "条数据";
    }
    @Override
    public String deleteById(Integer id) {

        int i = payMapper.deleteByPrimaryKey(id);

        return "成功的删除" + i + "条数据";
    }
    @Override
    public String update(Pay pay) {

        int i = payMapper.updateByPrimaryKeySelective(pay);

        return "成功的更新" + i + "条数据";
    }
    @Override
    public Pay getById(Integer id) {

        return payMapper.selectByPrimaryKey(id);
    }
    @Override
    public List<Pay> getAll() {
        return payMapper.selectAll();
    }
}
