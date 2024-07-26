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

import com.openclassrooms.RentalProject.model.dto.UserDto;
import com.openclassrooms.RentalProject.model.request.LoginRequest;
import com.openclassrooms.RentalProject.model.request.RegisterRequest;
import com.openclassrooms.RentalProject.model.response.AuthSuccessResponse;
import com.openclassrooms.RentalProject.service.AuthenticationService;
import com.openclassrooms.RentalProject.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

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
			@Content(mediaType = "application/json", schema = @Schema(implementation = AuthSuccessResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "The credentials are wrong", content = @Content)})
	@PostMapping("/api/auth/login")
	public ResponseEntity<AuthSuccessResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			String token = authenticationService.login(loginRequest);
			return ResponseEntity.ok(new AuthSuccessResponse(token));
		} catch (Exception exception) {
			return new ResponseEntity<AuthSuccessResponse>(HttpStatus.UNAUTHORIZED);
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
			@Content(mediaType = "application/json", schema = @Schema(implementation = AuthSuccessResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Input data are missing or not valid", content = @Content),
			@ApiResponse(responseCode = "409", description = "The email is already registered", content = @Content) })
	@PostMapping("/api/auth/register")
	public ResponseEntity<AuthSuccessResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

		if (userService.getUserByEmail(registerRequest.getEmail()) != null) {
			return new ResponseEntity<AuthSuccessResponse>(HttpStatus.CONFLICT);
		}

		String token = authenticationService.register(registerRequest);
		return ResponseEntity.ok(new AuthSuccessResponse(token));
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
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content) })
	@SecurityRequirement(name = "Bearer Authentication")
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
