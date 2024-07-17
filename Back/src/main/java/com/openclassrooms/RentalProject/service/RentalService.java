package com.openclassrooms.RentalProject.service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.DTO.RentalDto;
import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.repository.RentalRepository;
import com.openclassrooms.RentalProject.repository.UserRepository;
 
@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PictureService pictureService;
	
	public Iterable<Rental> getRentals() {
		return rentalRepository.findAll();
	}
	
	public Optional<Rental> getRentalById(Integer id) {
		return rentalRepository.findById(id);
	}
	
	public void saveRental(RentalDto rentalDto) throws Exception {
		Rental rental = modelMapper.map(rentalDto, Rental.class);
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new Exception();
		}
		
		Date now = new Date();
		rental.setCreationDate(now);
		rental.setUpdateDate(now);
		
		String picturePath = pictureService.saveImageToStorage(rentalDto.getPicture());
		rental.setPicture(picturePath);
		
		rentalRepository.save(rental);		
	}
}