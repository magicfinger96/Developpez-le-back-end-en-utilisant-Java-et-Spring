package com.openclassrooms.RentalProject.DTO;

import lombok.Data;

/**
 * Represents the request to Log in.
 */
@Data
public class LoginRequest {

	private String email;

	private String password;
}
