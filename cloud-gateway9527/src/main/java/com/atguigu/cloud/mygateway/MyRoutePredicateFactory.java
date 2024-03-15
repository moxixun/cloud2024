package com.atguigu.cloud.mygateway;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    //配置自己的断言
    @Setter
    @Getter
    @Validated
    public static class Config {
        @NotNull
        private String userType;

    }
    public MyRoutePredicateFactory(){
        super(Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return serverWebExchange -> {

            String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
            if(userType == null){
                return false;
            }
            return userType.equalsIgnoreCase(config.getUserType());
        };
    }
}
