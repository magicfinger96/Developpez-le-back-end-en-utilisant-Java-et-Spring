package com.openclassrooms.RentalProject.DTO;

import lombok.Data;

/**
 * Represents a request of Message creation.
 */
@Data
public class MessageRequest {
	int rental_id;
	int user_id;
	String message;
}
