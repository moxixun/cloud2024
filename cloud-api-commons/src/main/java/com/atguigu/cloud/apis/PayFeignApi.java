package com.atguigu.cloud.apis;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.response.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cloud-gateway")
public interface PayFeignApi {

    @PostMapping("/pay/add")
    ResultData add(@RequestBody PayDTO payDTO);

    @PostMapping("/pay/del/{id}")
    ResultData deleteById(@PathVariable("id") Integer id);

    @PostMapping("/pay/update")
    ResultData update(@RequestBody PayDTO payDTO);

//    @GetMapping("/pay/get/{id}")
//    ResultData getById(@PathVariable("id") Integer id);

    //验证feign是否带有客户端负载均衡功能
    @GetMapping("/pay/get/info")
    String getInfo();

    //测试circuitBreaker的服务熔断和降级
    @GetMapping(value = "/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id);

    //测试circuitBulkhead仓壁隔离-包括信号量方式和线程池方式
    @GetMapping(value = "/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id);

    /**
     * 链路追踪测试（micrometer+zipkin）
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例01
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/gateway/get/{id}")
    ResultData getById(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例02
     * @return
     */
    @GetMapping(value = "/pay/gateway/info")
    ResultData<String> getGatewayInfo();

}
