package com.openclassrooms.RentalProject.model.dto;

import lombok.Data;

/**
 * DTO of a list of rentals.
 */
@Data
public class RentalsDto {
	private Iterable<RentalDto> rentals;
}
