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

import com.openclassrooms.RentalProject.DTO.RentalDto;
import com.openclassrooms.RentalProject.DTO.RentalResponseDto;
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
    @GetMapping("/rentals")
    public Iterable<Rental> getRentals() {
        return rentalService.getRentals();
    }
    
    /**
    * Read - Get a rental with given ID
    * @param ID of the rental
    * @return - A Rental
    */
    @GetMapping("/rental/{id}")
    public Rental getRental(@PathVariable("id") final Integer id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        if(rental.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental not found.");	
        }
        return rental.get();
    }

	@PostMapping("/rentals")
	public ResponseEntity<RentalResponse> createRental(
			@Valid @RequestParam("name") String name,
			@Valid @RequestParam("surface") String surface,
			@Valid @RequestParam("price") String price,
			@Valid @RequestParam("picture") MultipartFile picture,
			@Valid @RequestParam("description") String description) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return new ResponseEntity<RentalResponse>(HttpStatus.UNAUTHORIZED);
		}
		
		RentalDto rentalDto = new RentalDto();
		Date now = new Date();
		rentalDto.setCreated_at(now);
		rentalDto.setUpdated_at(now);
		
		rentalDto.setOwner_id(user.getId());
		rentalDto.setName(name);
		rentalDto.setSurface(Float.parseFloat(surface));
		rentalDto.setPrice(Float.parseFloat(price));
		rentalDto.setDescription(description);
		
		String picturePath;
		try {
			picturePath = imageService.saveImage(picture);
		} catch (IOException e) {
			System.out.println("dede " + e);
			return new ResponseEntity<RentalResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		rentalDto.setPicture(picturePath);
		
		try {
			rentalService.saveRental(rentalDto);
		} catch (NotFoundException e) {
			return new ResponseEntity<RentalResponse>(HttpStatus.UNAUTHORIZED);
		}
		
		RentalResponse response = new RentalResponse();
		response.setMessage("Rental created !");
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Update - Update an existing rental
	 * @param id - The id of the rental to update
	 * @param rental - The Rental object updated
	 * @return
	 */
	@PutMapping("/rental/{id}")
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