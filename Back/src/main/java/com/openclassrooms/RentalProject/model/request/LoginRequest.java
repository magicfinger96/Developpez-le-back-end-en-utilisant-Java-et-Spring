package com.openclassrooms.RentalProject.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Represents the request to Log in.
 */
@Data
public class LoginRequest {

	@NotBlank(message = "The email is required and can't be empty.")
	private String email;

	@NotBlank(message = "The password is required and can't be empty.")
	private String password;
}
