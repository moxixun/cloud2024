package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Pay;

import java.util.List;

public interface PayService {

    public String add(Pay pay);
    public String deleteById(Integer id);
    public String update(Pay pay);

    public Pay getById(Integer id);
    public List<Pay> getAll();

}
