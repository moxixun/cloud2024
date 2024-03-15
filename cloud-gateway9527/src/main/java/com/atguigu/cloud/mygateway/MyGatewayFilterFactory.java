package com.atguigu.cloud.mygateway;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("status");
    }
    //空参构造
    public MyGatewayFilterFactory(){
        super(MyGatewayFilterFactory.Config.class);
    }
    @Override
    public GatewayFilter apply(MyGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("进入自定义网关过滤器MyGatewayFilterFactory,status:" + config.getStatus());
            if(request.getQueryParams().containsKey("moxixun")){
                return chain.filter(exchange);//请求参数包含键为moxixun则放行
            }
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        };
    }
    @Setter
    @Getter
    public static class Config{
        private String status;
    }
}
