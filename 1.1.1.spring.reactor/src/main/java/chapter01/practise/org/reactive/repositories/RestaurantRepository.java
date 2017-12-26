package chapter01.practise.org.reactive.repositories;

import chapter01.practise.org.reactive.model.Restaurant;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RestaurantRepository extends ReactiveCrudRepository<Restaurant, String> {
}
