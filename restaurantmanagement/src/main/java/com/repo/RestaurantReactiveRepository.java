package com.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.model.Restaurant;

import reactor.core.publisher.Flux;

@Repository
public interface RestaurantReactiveRepository extends ReactiveMongoRepository<Restaurant, String>{
	Flux<Restaurant> findRestaurantByRestaurantId(String restaurantId);
}
