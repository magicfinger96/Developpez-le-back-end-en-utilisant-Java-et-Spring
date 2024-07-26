package com.openclassrooms.RentalProject.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.model.dto.RentalDto;
import com.openclassrooms.RentalProject.model.dto.RentalsDto;
import com.openclassrooms.RentalProject.model.entity.Rental;
import com.openclassrooms.RentalProject.model.entity.User;
import com.openclassrooms.RentalProject.repository.RentalRepository;

/**
 * Service which handles rentals logic.
 */
@Service
public class RentalService {

	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Get all the rentals.
	 * 
	 * @return the rentals.
	 */
	public RentalsDto getRentals() {
		List<Rental> rentals = rentalRepository.findAll();

		List<RentalDto> rentalsDtoList = rentals.stream().map(rental -> modelMapper.map(rental, RentalDto.class))
				.toList();

		RentalsDto rentalsDto = new RentalsDto();
		rentalsDto.setRentals(rentalsDtoList);
		return rentalsDto;
	}

	/**
	 * Get a rental DTO.
	 * 
	 * @param id id of the fetched rental.
	 * @return a RentalDto
	 */
	public Optional<RentalDto> getRentalDtoById(Integer id) {
		Optional<Rental> rental = rentalRepository.findById(id);

		if (rental.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(modelMapper.map(rental, RentalDto.class));
	}

	/**
	 * Get a rental.
	 * 
	 * @param id id of the rental.
	 * @return a rental.
	 */
	public Optional<Rental> getRentalById(Integer id) {
		return rentalRepository.findById(id);
	}

	/**
	 * Save a rental.
	 * 
	 * @param rentalDto dto of the rental to save.
	 * @throws Exception thrown if the owner is not found.
	 */
	public void saveRental(RentalDto rentalDto) throws Exception {
		Rental rental = modelMapper.map(rentalDto, Rental.class);
		Optional<User> owner = userService.getUserById(rentalDto.getOwner_id());

		if (owner.isEmpty()) {
			throw new Exception();
		}

		rental.setOwner(owner.get());
		rentalRepository.save(rental);
	}
}