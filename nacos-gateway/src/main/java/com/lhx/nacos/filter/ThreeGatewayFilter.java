package com.lhx.nacos.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// @Component
public class ThreeGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("pre-filter-【333】 ");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println("post-filter-【333】 ");
        }));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
