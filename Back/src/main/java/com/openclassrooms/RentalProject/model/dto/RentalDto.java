package com.openclassrooms.RentalProject.model.dto;

import java.util.Date;

import lombok.Data;

/***
 * DTO of a rental.
 */
@Data
public class RentalDto {
	private int id;
	private String name;
	private int surface;
	private int price;
	private String picture;
	private String description;
	private int owner_id;
	private Date created_at;
	private Date updated_at;
}
