package com.openclassrooms.RentalProject.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {
	int id;
	String name;
	String email;
	Date created_at;
	Date updated_at;
}
