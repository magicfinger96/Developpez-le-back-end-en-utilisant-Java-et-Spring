package com.openclassrooms.RentalProject.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Represents a register request.
 */
@Data
public class RegisterRequest {

	@Email
	@NotBlank(message = "The email is required and can't be empty.")
	private String email;

	@NotBlank(message = "The name is required and can't be empty.")
	private String name;

	@NotBlank(message = "The password is required and can't be empty.")
	private String password;

}