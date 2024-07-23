package com.openclassrooms.RentalProject.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.DTO.RentalDto;
import com.openclassrooms.RentalProject.DTO.UserDto;
import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.model.User;

/**
 * Service which handles the ModelMapper.
 */
@Service
public class ModelMapperService {

	public ModelMapper createModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(User.class, UserDto.class).addMappings(mapper -> {
			mapper.map(src -> src.getCreationDate(), UserDto::setCreated_at);
			mapper.map(src -> src.getUpdateDate(), UserDto::setUpdated_at);
		});

		modelMapper.typeMap(Rental.class, RentalDto.class).addMappings(mapper -> {
			mapper.map(src -> src.getCreationDate(), RentalDto::setCreated_at);
			mapper.map(src -> src.getUpdateDate(), RentalDto::setUpdated_at);
			mapper.map(src -> src.getOwner().getId(), RentalDto::setOwner_id);
		});

		modelMapper.typeMap(RentalDto.class, Rental.class).addMappings(mapper -> {
			mapper.map(src -> src.getCreated_at(), Rental::setCreationDate);
			mapper.map(src -> src.getUpdated_at(), Rental::setUpdateDate);
		});

		return modelMapper;
	}
}