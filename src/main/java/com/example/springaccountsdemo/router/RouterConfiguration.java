package com.example.springaccountsdemo.router;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions
                .route()
                .GET("/hello", handler::hello)
                .build();
    }

    @Component
    public static class Handler {

        public Mono<ServerResponse> hello(ServerRequest request) {
            return new HelloHandlerFunction().handle(request);
        }

        public Mono<ServerResponse> cache(ServerRequest request, ServerResponse response) {
            return new CacheHandlerFunction().handle(request);
        }

    }

    @NonNullApi
    public static class HelloHandlerFunction implements HandlerFunction<ServerResponse> {

        @Override
        public Mono<ServerResponse> handle(@NonNull ServerRequest serverRequest) {

            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("Hello from handler function");
        }
    }

    @NonNullApi
    public static class CacheHandlerFunction implements HandlerFunction<ServerResponse> {

        @Override
        public Mono<ServerResponse> handle(@NonNull ServerRequest request) {
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("Hello from cache function");
        }
    }


}
