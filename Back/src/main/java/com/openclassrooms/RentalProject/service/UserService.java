package com.openclassrooms.RentalProject.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}
	
	public UserDto getUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return modelMapper.map(user, UserDto.class);
	}
	
	public Optional<UserDto> getUserDtoById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(modelMapper.map(user, UserDto.class));
	}
}