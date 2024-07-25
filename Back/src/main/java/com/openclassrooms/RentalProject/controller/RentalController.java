package com.openclassrooms.RentalProject.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.RentalProject.DTO.RentalsDto;
import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.DTO.MessageResponse;
import com.openclassrooms.RentalProject.DTO.RentalDto;
import com.openclassrooms.RentalProject.service.ImageService;
import com.openclassrooms.RentalProject.service.RentalService;
import com.openclassrooms.RentalProject.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Handles the end points related to the rental.
 */
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	/***
	 * End point that provides all the rentals.
	 * 
	 * @return a ResponseEntity containing the rentals.
	 */
	@Operation(summary = "Get all the rentals")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the rentals", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RentalsDto.class)) }),
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
	@GetMapping("/api/rentals")
	public ResponseEntity<RentalsDto> getRentals() {
		return ResponseEntity.ok(rentalService.getRentals());
	}

	/**
	 * End point that provides a rental.
	 * 
	 * @param id id of the rental.
	 * @return a ResponseEntity containing the rental if the call succeeded.
	 *         Otherwise, returns a error ResponseEntity.
	 */
	@Operation(summary = "Get a rental")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the rental", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDto.class)) }),
			@ApiResponse(responseCode = "404", description = "The rental was not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
	@GetMapping("/api/rentals/{id}")
	public ResponseEntity<RentalDto> getRental(@PathVariable("id") final Integer id) {

		Optional<RentalDto> rentalDto = rentalService.getRentalDtoById(id);

		if (rentalDto.isPresent()) {
			return ResponseEntity.ok(rentalDto.get());
		}
		return new ResponseEntity<RentalDto>(HttpStatus.NOT_FOUND);
	}

	/**
	 * End point that creates a rental.
	 * 
	 * @param name        name of the rental.
	 * @param surface     surface of the rental.
	 * @param price       price of the rental.
	 * @param picture     picture of the rental.
	 * @param description description of the rental.
	 * @return a ResponseEntity containing a successful message if the creation
	 *         succeeded. Otherwise, returns an error ResponseEntity.
	 */
	@Operation(summary = "Create a rental")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created the rental", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = @Content),
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content),
			@ApiResponse(responseCode = "413", description = "The picture is too large.", content = @Content),
			@ApiResponse(responseCode = "500", description = "Couldn't upload the picture", content = @Content) })
	@RequestMapping(
		    path = "/api/rentals", 
		    method = RequestMethod.POST, 
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> createRental(
			@Size(max = 60) @NotBlank @RequestParam("name") String name,
			@DecimalMax(value = "100000000000") @Positive @RequestParam("surface") int surface,
			@DecimalMax(value = "100000000000") @Positive @RequestParam("price") int price,
			@Valid @RequestPart("picture") MultipartFile picture,
			@Size(max = 400) @NotBlank @RequestParam("description") String description) {

		if (!picture.getContentType().equals("image/jpeg") && !picture.getContentType().equals("image/png")) {
			return new ResponseEntity<MessageResponse>(HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return new ResponseEntity<MessageResponse>(HttpStatus.UNAUTHORIZED);
		}

		UserDto user = userService.getUserByEmail(authentication.getName());
		if (user == null) {
			return new ResponseEntity<MessageResponse>(HttpStatus.UNAUTHORIZED);
		}

		RentalDto rentalDto = new RentalDto();
		rentalDto.setOwner_id(user.getId());
		rentalDto.setName(name);
		rentalDto.setSurface(surface);
		rentalDto.setPrice(price);
		rentalDto.setDescription(description);

		String picturePath;
		try {
			picturePath = imageService.uploadFile(picture);
		} catch (IOException e) {
			System.out.println("Error while uploading the file: " + e);
			return new ResponseEntity<MessageResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		rentalDto.setPicture(picturePath);

		try {
			rentalService.saveRental(rentalDto);
		} catch (Exception e) {
			System.out.println("Error while saving the rental: " + e);
			return new ResponseEntity<MessageResponse>(HttpStatus.UNAUTHORIZED);
		}

		MessageResponse response = new MessageResponse("Rental created !");

		return ResponseEntity.ok(response);
	}

	/**
	 * End point that updates a rental.
	 * 
	 * @param id          id of the rental.
	 * @param name        updated name of the rental.
	 * @param surface     updated surface of the rental.
	 * @param price       updated price of the rental.
	 * @param description updated description of the rental.
	 * @return a ResponseEntity containing a message if the update succeeded.
	 *         Otherwise, an error ResponseEntity.
	 */
	@Operation(summary = "Update a rental")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Updated the rental", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "The rental or the owner was not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = @Content),
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content)})
	@PutMapping("/api/rentals/{id}")
	public ResponseEntity<MessageResponse> updateRental(
			@PathVariable("id") final Integer id,
			@Size(max = 60) @NotBlank @RequestParam String name,
			@DecimalMax(value = "100000000000") @Positive @RequestParam int surface,
			@DecimalMax(value = "100000000000") @Positive @RequestParam int price,
			@Size(max = 400) @NotBlank @RequestParam String description) {

		Optional<RentalDto> rentalToUpdate = rentalService.getRentalDtoById(id);

		if (rentalToUpdate.isEmpty()) {
			return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
		}

		RentalDto rentalToSave = rentalToUpdate.get();
		rentalToSave.setName(name);
		rentalToSave.setSurface(surface);
		rentalToSave.setPrice(price);
		rentalToSave.setDescription(description);
		rentalToSave.setUpdated_at(new Date());

		try {
			rentalService.saveRental(rentalToSave);
		} catch (Exception e) {
			System.out.println("Failed to save the rental: " + e);
			return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
		}

		MessageResponse response = new MessageResponse("Rental updated !");
		return ResponseEntity.ok(response);
	}
}