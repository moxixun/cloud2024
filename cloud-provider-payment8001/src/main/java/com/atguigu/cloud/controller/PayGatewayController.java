package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.response.ResultData;
import com.atguigu.cloud.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@RestController
@Slf4j
public class PayGatewayController {

    @Resource
    private PayService payService;

    /**
     * 检查是否能够通过gateway访问到该路径
     * @param id
     * @return
     */
    @GetMapping("/pay/gateway/get/{id}")
    public ResultData<Pay> getById(@PathVariable("id") Integer id){

        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @GetMapping("/pay/gateway/info")
    public ResultData<String> getGatewayInfo(){

        return ResultData.success("gateway info test："+ IdUtil.simpleUUID());
    }


    @GetMapping("/pay/gateway/filter")
    public ResultData<String> getGatewayFilter(HttpServletRequest httpServletRequest){

        String result = "";
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()){

            String headName = headerNames.nextElement();
            String headValue = httpServletRequest.getHeader(headName);
            System.out.println("请求头名: " + headName +"\t\t\t"+"请求头值: " + headValue);
            if(headName.equalsIgnoreCase("X-Request-atguigu1")
                    || headName.equalsIgnoreCase("X-Request-atguigu2")) {
                result = result + headName + "\t " + headValue +" ";
            }
        }
        System.out.println("=============================================");
        String customerId = httpServletRequest.getParameter("customerId");
        System.out.println("request Parameter customerId: "+customerId);

        String customerName = httpServletRequest.getParameter("customerName");
        System.out.println("request Parameter customerName: "+customerName);
        System.out.println("=============================================");
        return ResultData.success("getGatewayFilter 过滤器 test： "+result+" \t "+ DateUtil.now());
    }

}
