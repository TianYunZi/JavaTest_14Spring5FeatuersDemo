package chapter01.practise.org.reactive.function;

import chapter01.practise.org.reactive.function.handler.RestaurantHandler;
import chapter01.practise.org.reactive.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.http.server.HttpServer;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class RestaurantServer implements CommandLineRunner {

    @Autowired
    private RestaurantHandler restaurantHandler;

    /**
     * 注册自定义RouterFunction
     *
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> restaurantRouter() {
        return RouterFunctions.route(RequestPredicates.GET("/reactive/restaurants")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), restaurantHandler::findAll)
                .andRoute(GET("/reactive/delay/restaurants").and(accept(APPLICATION_JSON_UTF8)), restaurantHandler::findAllDelay)
                .andRoute(GET("/reactive/restaurants/{id}").and(accept(APPLICATION_JSON_UTF8)), restaurantHandler::get)
                .andRoute(POST("/reactive/restaurants").and(accept(APPLICATION_JSON_UTF8)).and(contentType(APPLICATION_JSON_UTF8)), restaurantHandler::create)
                .andRoute(DELETE("/reactive/restaurants/{id}").and(accept(APPLICATION_JSON_UTF8)),
                        restaurantHandler::delete)
                // 注册自定义HandlerFilterFunction
                .filter((request, next) -> {
                    if (HttpMethod.PUT.equals(request.method())) {
                        return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
                    }
                    return next.handle(request);
                });
    }

    @Override
    public void run(String... args) throws Exception {
        RouterFunction<ServerResponse> router = restaurantRouter();
        // 转化为通用的Reactive HttpHandler
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);
        // 适配成Netty Server所需的Handler
        ReactorHttpHandlerAdapter httpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建Netty Server
        HttpServer server = HttpServer.create("123.206.109.156", 27017);
        // 注册Handler并启动Netty Server
        server.newHandler(httpHandlerAdapter).block();
    }
}
