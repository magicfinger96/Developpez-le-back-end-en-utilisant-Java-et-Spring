package com.openclassrooms.RentalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.openclassrooms.RentalProject.DTO.AuthSuccessDto;
import com.openclassrooms.RentalProject.DTO.LoginDto;
import com.openclassrooms.RentalProject.DTO.RegisterDto;
import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.service.AuthenticationService;
import com.openclassrooms.RentalProject.service.UserService;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;

	@PostMapping("/api/auth/login")
	public ResponseEntity<AuthSuccessDto> login(@RequestBody LoginDto loginDto) {
		try {
			AuthSuccessDto authSuccess = authenticationService.login(loginDto);
			return ResponseEntity.ok(authSuccess);
		} catch (Exception exception) {
			return new ResponseEntity<AuthSuccessDto>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/api/auth/register")
	public ResponseEntity<AuthSuccessDto> register(@RequestBody RegisterDto registerDto) {

		String name = registerDto.getName();
		String email = registerDto.getEmail();
		String password = registerDto.getPassword();

		if (name == null || name.isBlank() || email == null || email.isBlank() || password == null
				|| password.isBlank()) {
			return new ResponseEntity<AuthSuccessDto>(HttpStatus.BAD_REQUEST);
		}

		if (userService.getUserByEmail(registerDto.getEmail()) != null) {
			return new ResponseEntity<AuthSuccessDto>(HttpStatus.CONFLICT);
		}

		try {
			return ResponseEntity.ok(authenticationService.register(registerDto));
		} catch (JOSEException e) {
			return new ResponseEntity<AuthSuccessDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/auth/me")
	public ResponseEntity<UserDto> getMe() {
		return ResponseEntity.ok(authenticationService.getMe());
	}
}
