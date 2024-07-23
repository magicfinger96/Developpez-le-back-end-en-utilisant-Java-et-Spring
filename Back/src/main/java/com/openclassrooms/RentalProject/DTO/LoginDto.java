package com.openclassrooms.RentalProject.DTO;

import lombok.Data;

/**
 * Represents the DTO of a login.
 */
@Data
public class LoginDto {

	private String email;

	private String password;
}
