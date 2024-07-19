package com.openclassrooms.RentalProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}