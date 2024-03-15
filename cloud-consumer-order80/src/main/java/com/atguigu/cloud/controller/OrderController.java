package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.response.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderController {

    @Resource
    private RestTemplate restTemplate;
//    public static final String PaymentSrv_URL = "http://localhost:8001";//硬编码
    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    /**
     * order80微服务添加订单，调用pay8001端
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求
     * 我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者
     * 参数可以不添加@RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/pay/add")
    public ResultData addOrder(PayDTO payDTO){

        log.info("订单微服务添加订单：{}", payDTO);

        return restTemplate
                .postForObject(PaymentSrv_URL + "/pay/add"
                        , payDTO
                        , ResultData.class);
    }
    /**
     * 删除订单
     */
    @GetMapping("/pay/del/{id}")
    public ResultData deleteOrderById(@PathVariable("id") Integer id){

        log.info("订单微服务删除订单，id:{}", id);

        return restTemplate
                .postForObject(PaymentSrv_URL + "/pay/del/" + id
                        , id
                        , ResultData.class);
    }
    /**
     * 修改订单
     * @param payDTO
     * @return
     */
    @GetMapping("/pay/update")
    public ResultData updateOrder(PayDTO payDTO){

        log.info("订单微服务修改订单，payDTO:{}", payDTO);

//        String json = JSON.toJSONString(payDTO);//转为json无效
//
//        log.info("json:{}", json);
        //通过post请求pay的update方法无法接收，将pay8001中的更新映射改为postMapping可以？
        return restTemplate
                .postForObject(PaymentSrv_URL + "/pay/update"
                        , payDTO
                        , ResultData.class);
    }

    @GetMapping("/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){

        log.info("订单微服务查询订单支付信息，id:{}", id);

        return restTemplate
                .getForObject(PaymentSrv_URL + "/pay/get/" + id
                        , ResultData.class
                        , id);
    }

    /**
     * 客户端负载均衡测试
     * --客户端请求在consul注册表中自动选择要拉取的服务(相同的服务中)
     */
    @GetMapping("/pay/getInfo")
    public String getBalanceInfo(){

        return restTemplate
                .getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }
}
