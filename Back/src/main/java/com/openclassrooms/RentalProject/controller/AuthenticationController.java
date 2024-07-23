package com.openclassrooms.RentalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.DTO.LoginDto;
import com.openclassrooms.RentalProject.DTO.MessageResponse;
import com.openclassrooms.RentalProject.DTO.RegisterRequest;
import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.service.AuthenticationService;
import com.openclassrooms.RentalProject.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Handles the end points related to the user authentication.
 */
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;

	/**
	 * End point logging the user in.
	 * 
	 * @param loginDto credentials used to log in.
	 * @return a ResponseEntity containing the jwt token if succeeded. Otherwise an
	 *         error ResponseEntity.
	 */
	@Operation(summary = "Log the user in")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Logged in the user", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "The credentials are wrong", content = @Content) })
	@PostMapping("/api/auth/login")
	public ResponseEntity<MessageResponse> login(@RequestBody LoginDto loginDto) {
		try {
			String token = authenticationService.login(loginDto);
			return ResponseEntity.ok(new MessageResponse(token));
		} catch (Exception exception) {
			return new ResponseEntity<MessageResponse>(HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * End point registering a user.
	 * 
	 * @param registerDto the data used to create a new user.
	 * @return a ResponseEntity containing the jwt token if succeeded. Otherwise an
	 *         error ResponseEntity.
	 */
	@Operation(summary = "Register the user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Registered the user", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Some input data are missing", content = @Content),
			@ApiResponse(responseCode = "409", description = "The email is already registered", content = @Content) })
	@PostMapping("/api/auth/register")
	public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest registerDto) {

		String name = registerDto.getName();
		String email = registerDto.getEmail();
		String password = registerDto.getPassword();

		if (name == null || name.isBlank() || email == null || email.isBlank() || password == null
				|| password.isBlank()) {
			return new ResponseEntity<MessageResponse>(HttpStatus.BAD_REQUEST);
		}

		if (userService.getUserByEmail(registerDto.getEmail()) != null) {
			return new ResponseEntity<MessageResponse>(HttpStatus.CONFLICT);
		}

		String token = authenticationService.register(registerDto);
		return ResponseEntity.ok(new MessageResponse(token));
	}

	/**
	 * End point that returns the authenticated user.
	 * 
	 * @return a ResponseEntity containing the user. Otherwise, returns a error
	 *         ResponseEntity.
	 */
	@Operation(summary = "Get the authenticated user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the authenticated user", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)), }),
			@ApiResponse(responseCode = "401", description = "There is no authenticated user", content = @Content) })
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/api/auth/me")
	public ResponseEntity<UserDto> getMe() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return new ResponseEntity<UserDto>(HttpStatus.UNAUTHORIZED);
		}

		String email = authentication.getName();

		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
}
