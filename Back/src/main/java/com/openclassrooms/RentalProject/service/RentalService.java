package com.openclassrooms.RentalProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.repository.RentalRepository;
 
@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	
	public Iterable<Rental> getRentals() {
		return rentalRepository.findAll();
	}
	
	public Optional<Rental> getRentalById(Integer id) {
		return rentalRepository.findById(id);
	}
	
	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);		
	}
}