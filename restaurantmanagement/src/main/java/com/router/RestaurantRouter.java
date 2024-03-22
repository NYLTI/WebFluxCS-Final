package com.router;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.handler.RestaurantHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class RestaurantRouter {
	@Bean
	public RouterFunction<ServerResponse> restaurantRoute(RestaurantHandler restaurantHandler){
		return route()
				.nest(path("/v1/restaurant"), builder ->
						builder
							.GET("", restaurantHandler::getRestaurant)
							.POST("", restaurantHandler::addRestaurant)
							.DELETE("/{restaurantId}", restaurantHandler::deleteRestaurant)
							.GET("/stream", restaurantHandler::getRestaurantStream)
							.PUT("", restaurantHandler::updateRestaurant)
				).build();
	}
}
