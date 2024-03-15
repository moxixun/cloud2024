package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.response.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pay")
@Slf4j
@Tag(name = "支付微服务模块",description = "支付CRUD")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping("/add")
    @Operation(summary = "新增支付记录",description = "新增支付流水方法,json串做参数")
    public ResultData<String> add(@RequestBody Pay pay){

        log.info("新增支付记录:{}", pay);

        String message = payService.add(pay);

        return ResultData.success(message);
    }

    @PostMapping(value = "/del/{id}")//删除应该是delete
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData<String> deleteById(@PathVariable("id") Integer id){

        log.info("删除支付记录:{}", id);

        String message = payService.deleteById(id);

        return ResultData.success(message);
    }

    @PostMapping(value = "/update")//更新应该是用put...
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData<String> update(@RequestBody PayDTO payDTO){

        log.info("修改支付记录:{}", payDTO);

        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);

        String message = payService.update(pay);
        return ResultData.success(message);
    }

    /**
     * 查询单条数据
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "按照ID查流水",description = "查询支付流水方法")
    public ResultData<Pay> getById(@PathVariable("id") Integer id){

        log.info("按照ID查流水:{}", id);

        if(id == -4) throw new RuntimeException("id为负数...");
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }
    /**
     * 查询全部数据
     */
    @GetMapping("/getAll")
    @Operation(summary = "查询全部流水",description = "查询支付流水方法")
    public ResultData<List<Pay>> getAll(){

        log.info("查询全部流水");
        List<Pay> all = payService.getAll();
        return ResultData.success(all);
    }
    //测试从consul中下拉配置
    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/get/info")
    private String consulInfo(@Value("${atguigu.info}") String info){

        return "port: "+ port + "  info:" + info;
    }
}
