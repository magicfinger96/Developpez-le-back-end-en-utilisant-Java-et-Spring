package com.openclassrooms.RentalProject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.model.dto.UserDto;
import com.openclassrooms.RentalProject.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Handles the end points related to the user.
 */
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Read - Get a user with given ID.
	 *
	 * @param id the id of the user.
	 * @return a ResponseEntity containing the UserDto if the call succeeded,
	 *         otherwise returns an error ResponseEntity.
	 */
	@Operation(summary = "Get a user by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the user", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content) })
	@GetMapping("/api/user/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable("id") final Integer id) {
		Optional<UserDto> user = userService.getUserDtoById(id);
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(user.get());
	}

}
