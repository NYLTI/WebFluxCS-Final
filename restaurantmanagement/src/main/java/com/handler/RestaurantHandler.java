package com.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.model.Restaurant;
import com.repo.RestaurantReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class RestaurantHandler {
	private RestaurantReactiveRepository repo;
	
	@Autowired
	public RestaurantHandler(RestaurantReactiveRepository repo) {
		this.repo = repo;
	}
	
	static Mono<ServerResponse> notFoundMono = ServerResponse.notFound().build();
	Sinks.Many<Restaurant> restaurantSink = Sinks.many().replay().latest();
	
	public Mono<ServerResponse> addRestaurant(ServerRequest serverRequest){
		return serverRequest.bodyToMono(Restaurant.class)
				.flatMap(restaurant -> repo.save(restaurant))
				.doOnNext(restaurant ->
					restaurantSink.tryEmitNext(restaurant)
				)
				.flatMap(savedRestaurant -> ServerResponse.status(HttpStatus.CREATED)
						.bodyValue(savedRestaurant));
	}
	
	public Mono<ServerResponse> getRestaurant(ServerRequest serverRequest){
		var restaurantId= serverRequest.queryParam("restaurantId");
		if(restaurantId.isPresent()) {
			var restaurant = repo.findRestaurantByRestaurantId(String.valueOf(restaurantId.get()));
			return buildRestaurantResponse(restaurant);
		}else {
			return buildRestaurantResponse(repo.findAll());
		}
	}
	
	public Mono<ServerResponse> updateRestaurant(ServerRequest serverRequest){
		return serverRequest.bodyToMono(Restaurant.class)
				.flatMap(restaurant -> repo.save(restaurant))
				.doOnNext(restaurant ->
					restaurantSink.tryEmitNext(restaurant)
				)
				.flatMap(saveRestaurant -> ServerResponse.status(HttpStatus.ACCEPTED)
						.bodyValue(saveRestaurant));
	}
	
	public Mono<ServerResponse> deleteRestaurant(ServerRequest serverRequest){
		var restaurantId = serverRequest.pathVariable("restaurantId");
		return repo.deleteById(String.valueOf(restaurantId))
				.then(ServerResponse.noContent().build());
	}
	
	public Mono<ServerResponse> getRestaurantStream(ServerRequest serverRequest){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_NDJSON)
				.body(restaurantSink.asFlux(), Restaurant.class)
				.log();
	}

	private Mono<ServerResponse> buildRestaurantResponse(Flux<Restaurant> restaurants) {
		return ServerResponse.ok().body(restaurants, Restaurant.class);
	}
}
