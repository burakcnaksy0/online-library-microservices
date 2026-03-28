package com.burakcanaksoy.apigateway.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> bookServiceRoute() {
        return route("book-service")
                .route(path("/v1/book/**"), http())
                .filter(lb("book-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> libraryServiceRoute() {
        return route("library-service")
                .route(path("/v1/library/**"), http())
                .filter(lb("library-service"))
                .build();
    }
}
