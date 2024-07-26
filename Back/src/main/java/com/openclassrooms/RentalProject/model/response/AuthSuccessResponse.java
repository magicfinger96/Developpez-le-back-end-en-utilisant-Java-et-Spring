package com.openclassrooms.RentalProject.model.response;

import lombok.Data;

/**
 * Represents the response sent when authentication succeeded.
 */
@Data
public class AuthSuccessResponse {

	private String token;

	public AuthSuccessResponse(String token) {
		this.token = token;
	}
}
