package com.openclassrooms.RentalProject.DTO;

import lombok.Data;

@Data
public class RentalsDto {
	private Iterable<RentalDto> rentals;
}
