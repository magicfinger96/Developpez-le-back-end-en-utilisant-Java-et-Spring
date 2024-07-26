package com.openclassrooms.RentalProject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.model.entity.User;
import com.openclassrooms.RentalProject.model.request.LoginRequest;
import com.openclassrooms.RentalProject.model.request.RegisterRequest;

/**
 * Service which handles the authentication logic.
 */
@Service
public class AuthenticationService {

	@Autowired
	public JWTService jwtService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Save a new user in the DDB. Encodes the password before.
	 * 
	 * @param registerRequest contains user data to save.
	 * @return the generated JWT.
	 */
	public String register(RegisterRequest registerRequest) {

		User user = modelMapper.map(registerRequest, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userService.saveUser(user);

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		return jwtService.generateToken(userDetails);
	}

	/**
	 * Check if the credentials are valid.
	 * 
	 * @param loginDto contains credentials.
	 * @return the generated JWT.
	 * @throws Exception if the user details is null.
	 */
	public String login(LoginRequest loginRequest) throws Exception {

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
		if (userDetails == null) {
			throw new Exception();
		}

		if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
			throw new Exception();
		}
		return jwtService.generateToken(userDetails);
	}
}
