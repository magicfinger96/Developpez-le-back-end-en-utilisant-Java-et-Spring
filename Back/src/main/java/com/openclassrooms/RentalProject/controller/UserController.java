package com.openclassrooms.RentalProject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Read - Get a user with given ID
	 * 
	 * @param ID of the user
	 * @return - A User
	 */
	public User getUser(@PathVariable("id") final Integer id) {
		Optional<User> user = userService.getUserById(id);
	@GetMapping("/api/user/{id}")
		if (user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
		}
		return user.get();
	}

}
