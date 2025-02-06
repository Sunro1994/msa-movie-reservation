package com.sparta.cloud.movie_reservation_gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomPreFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("request Url = {}", exchange.getRequest().getURI());
        log.info("request method= {}", exchange.getRequest().getMethod());
        log.info("request data = {}", exchange.getRequest().getBody());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
