package com.openclassrooms.RentalProject.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.openclassrooms.RentalProject.DTO.RentalsDto;
import com.openclassrooms.RentalProject.DTO.RentalDto;
import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.repository.RentalRepository;
import com.openclassrooms.RentalProject.repository.UserRepository;
 
@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RentalsDto getRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		
		List<RentalDto> rentalsDtoList = rentals.stream().map(rental -> modelMapper.map(rental, RentalDto.class)).toList();
		
		RentalsDto rentalsDto = new RentalsDto();
		rentalsDto.setRentals(rentalsDtoList);
		return rentalsDto;
	}
	
	public RentalDto getRentalDtoById(Integer id) throws NotFoundException {
		Optional<Rental> rental = rentalRepository.findById(id);
		
		if(rental.isEmpty()) {
			throw new NotFoundException();
		}
		
		return modelMapper.map(rental, RentalDto.class);
	}
	
	public Optional<Rental> getRentalById(Integer id) {
		return rentalRepository.findById(id);
	}
	
	public void saveRental(RentalDto rentalDto) throws NotFoundException {
		Rental rental = modelMapper.map(rentalDto, Rental.class);
		Optional<User> owner = userService.getUserById(rentalDto.getOwner_id());
		
		if(owner.isEmpty()) {
			throw new NotFoundException();
		}
		
		rental.setOwner(owner.get());
		rentalRepository.save(rental);	
	}
}