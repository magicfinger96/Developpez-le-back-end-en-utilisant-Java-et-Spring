package com.openclassrooms.RentalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.DTO.AuthSuccessDto;
import com.openclassrooms.RentalProject.DTO.LoginDto;
import com.openclassrooms.RentalProject.DTO.RegisterDto;
import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	
	@PostMapping("/auth/login")
	public ResponseEntity<AuthSuccessDto> login(@RequestBody LoginDto loginDto) {
		try {
			AuthSuccessDto authSuccess = authenticationService.login(loginDto);
			return ResponseEntity.ok(authSuccess);
		} catch(Exception exception) {
			return new ResponseEntity<AuthSuccessDto>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/auth/register")
	public ResponseEntity<AuthSuccessDto> register(@RequestBody RegisterDto registerDto) {
		return ResponseEntity.ok(authenticationService.register(registerDto));
	}
	
	@GetMapping("/auth/me")
	public ResponseEntity<UserDto> getMe() {
		return ResponseEntity.ok(authenticationService.getMe());
	}
}
