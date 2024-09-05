package com.example.springaccountsdemo.router;

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
                .GET("/test", handler::test)
                .build();
    }

    @Component
    public static class Handler {

        public Mono<ServerResponse> hello(ServerRequest request) {
            return new HelloHandlerFunction().handle(request);
        }

        public Mono<ServerResponse> test(ServerRequest request) {
            return new TestHandlerFunction().handle(request);
        }

    }

    @Component
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

    @Component
    @NonNullApi
    public static class TestHandlerFunction implements HandlerFunction<ServerResponse> {
        @Override
        public Mono<ServerResponse> handle(@NonNull ServerRequest request) {
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("Hello from handler test function");
        }
    }


}
