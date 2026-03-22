package com.burakcanaksoy.apigateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> bookServiceRoute() {
        return GatewayRouterFunctions.route("book-service")
                .route(GatewayRequestPredicates.path("/v1/book/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("book-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> libraryServiceRoute() {
        return GatewayRouterFunctions.route("library-service")
                .route(GatewayRequestPredicates.path("/v1/library/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("library-service"))
                .build();
    }
}
