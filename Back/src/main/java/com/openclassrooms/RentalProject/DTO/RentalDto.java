package com.openclassrooms.RentalProject.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class RentalDto {
	private int id;
	private String name;
	private float surface;
	private float price;
	private String picture;
	private String description;
	private int owner_id;
	private Date created_at;
	private Date updated_at;
}
