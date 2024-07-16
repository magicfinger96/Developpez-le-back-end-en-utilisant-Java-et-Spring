package com.openclassrooms.RentalProject.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.DTO.AuthSuccessDto;
import com.openclassrooms.RentalProject.DTO.LoginDto;
import com.openclassrooms.RentalProject.DTO.RegisterDto;
import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	public JWTService jwtService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	
	public AuthSuccessDto register(RegisterDto registerDto) {
		
		User user = modelMapper.map(registerDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Date date = new Date();
		user.setCreationDate(date);
		user.setUpdateDate(date);
		
		userRepository.save(user);
	
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		
		AuthSuccessDto success = new AuthSuccessDto();
		success.setToken(jwtService.generateToken(userDetails));
		return success;
	}
	
	public AuthSuccessDto login(LoginDto loginDto) throws Exception {
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getEmail());
		if (userDetails == null) {
			throw new Exception();
		}
		
		if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
			throw new Exception();
		}
		
		AuthSuccessDto success = new AuthSuccessDto();
		success.setToken(jwtService.generateToken(userDetails));
		return success;
	}
	
	public UserDto getMe() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email);
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}
}
