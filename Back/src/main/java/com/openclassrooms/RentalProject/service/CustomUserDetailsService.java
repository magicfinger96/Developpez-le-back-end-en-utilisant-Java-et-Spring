package com.openclassrooms.RentalProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.model.entity.User;
import com.openclassrooms.RentalProject.repository.UserRepository;

/**
 * Service handling the UserDetails.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository dbUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dbUserRepository.findByEmail(username);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getGrantedAuthorities("USER"));
	}

	/**
	 * Returns the granted authorities.
	 *
	 * @param role role we want to add inside the authorities.
	 * @return a list of GrantedAuthority with only one authority.
	 */
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}