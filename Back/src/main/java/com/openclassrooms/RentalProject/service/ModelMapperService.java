package com.openclassrooms.RentalProject.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.model.User;

@Service
public class ModelMapperService {
	
	public ModelMapper createModelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	    modelMapper.typeMap(User.class, UserDto.class).addMappings(mapper -> {
			  mapper.map(src -> src.getCreationDate(),
					  UserDto::setCreated_at);
			  mapper.map(src -> src.getUpdateDate(),
					  UserDto::setUpdated_at);
			});
	    return modelMapper;
	}
}
