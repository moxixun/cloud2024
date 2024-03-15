package com.atguigu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RateLimitController {

    @GetMapping("/test/byResource")
    @SentinelResource(value = "byResource", blockHandler = "byResource_blockHandler")
    public String byResource(){

        return "test...@SentinelResource";
    }
    public String byResource_blockHandler(BlockException blockException){
        return "已被限流，进入byResource_blockHandler...";
    }

    /**
     * 通过@SentinelResource 测试服务的兜底方法
     * 分为两种：1.blockHandle用来处理sentinel中自定义的限流请求返回数据方案
     *         2.fallback用来处理服务抛出异常时返回数据方案
     * @param id
     * @return
     */
    @GetMapping("/test/fallbackAndBlockHandle/{id}")
    @SentinelResource(value = "fallbackAndBlockHandle"
            , blockHandler = "returnBlockHandle", fallback = "returnFallback")
    public String fallbackAndBlockHandle(@PathVariable("id") Integer id){

        if(id == 0){
            throw new RuntimeException("参数为零...");
        }
        return "fallbackAndBlockHandle...test";
    }
    //blockHandle兜底方法
    public String returnBlockHandle(@PathVariable("id") Integer id, BlockException blockException){
        return "returnBlockHandle, 违背了sentinel定义的限流规则...";
    }
    //fallback兜底方法
    public String returnFallback(@PathVariable("id") Integer id, Throwable e){
        return "服务出现异常..." + e.getMessage();
    }
    /**
     * 对热点参数进行限流
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "testHotKeyBlockHandle")
    public String testHotKey(
              @RequestParam(value = "p1", required = false) String p1
            , @RequestParam(value = "p2", required = false) String p2){
        return "正常访问， p1:" + p1 + "p2:" + p2;
    }
    public String testHotKeyBlockHandle(
              @RequestParam(value = "p1", required = false) String p1
            , @RequestParam(value = "p2", required = false) String p2
            , BlockException blockException){
        return "p1热点限流.... ";
    }
}
