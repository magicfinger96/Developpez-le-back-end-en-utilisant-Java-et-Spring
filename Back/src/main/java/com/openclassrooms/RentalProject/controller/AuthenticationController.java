package com.openclassrooms.RentalProject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.service.JWTService;

@RestController
public class AuthenticationController {

	public JWTService jwtService;
	
	
	public AuthenticationController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping("/auth/login")
	public String login(Authentication authentication) {
		String token = jwtService.generateToken(authentication);
		return token;
	}
	
	@PostMapping("/auth/register")
	public String register() {
		String token = jwtService.generateToken(authentication);
		return token;
	}
	
	@GetMapping("/auth/me")
	public User getMe() {
		return null;
	}
}
