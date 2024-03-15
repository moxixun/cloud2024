package com.atguigu.cloud.mygateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    public static final String BEGIN_TIME = "begin_time";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(BEGIN_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long begin = (Long) exchange.getAttributes().get(BEGIN_TIME);
            if(begin != null){
                log.info("访问接口主机：" + exchange.getRequest().getURI().getHost());
                log.info("访问接口端口：" + exchange.getRequest().getURI().getPort());
                log.info("访问接口路径：" + exchange.getRequest().getURI().getPath());
                log.info("访问接口参数：" + exchange.getRequest().getURI().getRawQuery());
                log.info("访问接口耗时：" + (System.currentTimeMillis() - begin) + "毫秒");
                log.info("====================================================");
                System.out.println();
            }
        }));
    }
    //设置我们自定义的全局过滤器的优先级，值越小优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
