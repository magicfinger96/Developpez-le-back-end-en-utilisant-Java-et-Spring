package com.openclassrooms.RentalProject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Represents a request of Message creation.
 */
@Data
public class MessageRequest {

	@NotNull(message = "The rental id is required.")
	int rental_id;

	@NotNull(message = "The user id is required.")
	int user_id;

	@NotBlank(message = "The message is required and can't be empty.")
	String message;
}
