package com.openclassrooms.RentalProject.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.service.RentalService;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    /**
    * Read - Get all rentals
    * @return - An Iterable object of Rental full filled
    */
    @GetMapping("/api/rentals")
    public Iterable<Rental> getRentals() {
        return rentalService.getRentals();
    }
    
    /**
    * Read - Get a rental with given ID
    * @param ID of the rental
    * @return - A Rental
    */
    @GetMapping("/api/rental/{id}")
    public Rental getRental(@PathVariable("id") final Integer id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        if(rental.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found.");	
        }
        return rental.get();
    }
    
	/**
	 * Create - Add a new rental
	 * @param rental An object Rental
	 * @return The Rental object saved
	 */
	@PostMapping("/api/rental")
	public Rental createEmployee(@RequestBody Rental rental) {
		return rentalService.saveRental(rental);
	}
	
	/**
	 * Update - Update an existing rental
	 * @param id - The id of the rental to update
	 * @param rental - The Rental object updated
	 * @return
	 */
	@PutMapping("/api/rental/{id}")
	public Rental updateEmployee(@PathVariable("id") final Integer id, @RequestBody Rental rental) {
		Optional<Rental> rentalToUpdate = rentalService.getRentalById(id);
		if(rentalToUpdate.isPresent()) {
			Rental currentRental = rentalToUpdate.get();
			
			String name = rental.getName();
			if(name != null) {
				currentRental.setName(name);
			}
			float surface = rental.getSurface();
			currentRental.setSurface(surface);
			
			float price = rental.getPrice();
			currentRental.setPrice(price);
			
			String description = rental.getDescription();
			if(description != null) {
				currentRental.setDescription(description);
			}
			
			currentRental.setUpdateDate(new Date());
			rentalService.saveRental(currentRental);
			return currentRental;
		} else {
			return null;
		}
	}
}