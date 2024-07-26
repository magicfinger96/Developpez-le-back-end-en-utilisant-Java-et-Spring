package com.openclassrooms.RentalProject.model.dto;

import java.util.Date;

import lombok.Data;

/**
 * DTO of a user.
 */
@Data
public class UserDto {
	int id;
	String name;
	String email;
	Date created_at;
	Date updated_at;
}
