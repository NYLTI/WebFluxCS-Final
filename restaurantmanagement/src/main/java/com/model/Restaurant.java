package com.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {
	@Id
	private String restaurantId;
	private String restaurantName;
	private String owner;
	private String location;
	private List<String> menu;
}
