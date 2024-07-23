package com.openclassrooms.RentalProject.DTO;

import lombok.Data;

/**
 * Represents a register request.
 */
@Data
public class RegisterRequest {

	private String email;

	private String name;

	private String password;

}