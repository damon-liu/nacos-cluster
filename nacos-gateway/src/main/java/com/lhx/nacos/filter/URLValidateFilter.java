package com.lhx.nacos.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 目标：模拟网关鉴权
 */
@Component
public class URLValidateFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("token验证！");
        //1.获取请求参数token
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        //2.判断token是否存在
        //如果不存在则拦截，提示用户未授权
        // if (!StringUtils.hasText(token)){
        //     //设置提示用户未授权
        //     exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //     //完结请求
        //     return exchange.getResponse().setComplete();
        // }
        //如果存在，则放行拦截器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
