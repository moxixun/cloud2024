package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.response.ResultData;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AccountFeignApi accountFeignApi;
    @Resource
    private StorageFeignApi storageFeignApi;
    /**
     * 用户下单，使用seata同步更新用户账户信息和库存信息
     * @param order
     */
    @GlobalTransactional(name = "moxixun_order_service", rollbackFor = Exception.class)
    public void createOrder(Order order) {

        //该次全局事务xid
        String xid = RootContext.getXID();

        //1.--新增订单
        log.info("-------------------开始新增用户订单，xid：" + xid);
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);

        if(result > 0){
            //新增订单成功,从数据库中获取新增的订单数据
            Order orderFromDB = orderMapper.selectOne(order);

            //2.--开始修改库存信息
            log.info("-------------------开始修改库存信息，xid：" + xid);
            ResultData storageResult = storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("-------------------结束修改库存信息，xid：" + xid);
            System.out.println();

            //3.--开始修改账户信息
            log.info("-------------------开始修改账户信息，xid：" + xid);
            ResultData accountResult = accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            log.info("-------------------结束修改账户信息，xid：" + xid);
            System.out.println();

            //将订单状态设置成为已完结
            orderFromDB.setStatus(1);
            //设置修改条件判断标准(where?)
            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId", orderFromDB.getUserId());
            criteria.andEqualTo("status", 0);

            orderMapper.updateByExampleSelective(orderFromDB, whereCondition);
            log.info("-------------------新增用户订单成功，xid：" + xid);
            log.info("-------------------新增用户订单成功，orderFromDB info：" + orderFromDB);
        }else {
            log.info("-------------------新增用户订单失败，xid：" + xid);
            throw new RuntimeException("订单插入失败");
        }
    }
}
