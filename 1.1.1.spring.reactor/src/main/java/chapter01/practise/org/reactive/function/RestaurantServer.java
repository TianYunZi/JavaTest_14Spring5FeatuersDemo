package chapter01.practise.org.reactive.function;

import chapter01.practise.org.reactive.function.handler.RestaurantHandler;
import chapter01.practise.org.reactive.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantServer implements CommandLineRunner {

    private RestaurantHandler restaurantHandler;

    public RestaurantServer(RestaurantHandler restaurantHandler) {
        this.restaurantHandler = restaurantHandler;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
