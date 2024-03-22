package com.application.restaurantmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@ComponentScan("com")
@EnableReactiveMongoRepositories("com")
@EntityScan("com")
public class RestaurantmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantmanagementApplication.class, args);
	}

}
