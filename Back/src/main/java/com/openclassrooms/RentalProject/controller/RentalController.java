package com.openclassrooms.RentalProject.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.RentalProject.DTO.RentalsDto;
import com.openclassrooms.RentalProject.DTO.RentalDto;
import com.openclassrooms.RentalProject.DTO.RentalResponse;
import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.service.ImageService;
import com.openclassrooms.RentalProject.service.RentalService;
import com.openclassrooms.RentalProject.service.UserService;

import jakarta.validation.Valid;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/rentals")
    public RentalsDto getRentals() {
        return rentalService.getRentals();
    }
    
    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalDto> getRental(@PathVariable("id") final Integer id) {
    	
    	Optional<RentalDto> rentalDto = rentalService.getRentalDtoById(id);
    	
    	if(rentalDto.isPresent()) {
    		return ResponseEntity.ok(rentalDto.get());
    	}
    	return new ResponseEntity<RentalDto>(HttpStatus.NOT_FOUND);
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
			picturePath = imageService.uploadFile(picture);
		} catch (IOException e) {
			System.out.println("Error while uploading the file: " + e);
			return new ResponseEntity<RentalResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		rentalDto.setPicture(picturePath);
		
		try {
			rentalService.saveRental(rentalDto);
		} catch (NotFoundException e) {
			System.out.println("Error while saving the rental: " + e);
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
	public Rental updateRental(@PathVariable("id") final Integer id, @RequestBody Rental rental) {
		Optional<Rental> rentalToUpdate = null;//rentalService.getRentalById(id);
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
			//rentalService.saveRental(currentRental);
			return currentRental;
		} else {
			return null;
		}
	}
}