package com.openclassrooms.RentalProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.DTO.MessageRequest;
import com.openclassrooms.RentalProject.model.Message;
import com.openclassrooms.RentalProject.model.Rental;
import com.openclassrooms.RentalProject.model.User;
import com.openclassrooms.RentalProject.repository.MessageRepository;

/**
 * Service which handles the messages from a user about a rental.
 */
@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RentalService rentalService;

	/**
	 * Save a message.
	 * 
	 * @param messageRequest data of the message.
	 * @throws Exception when the user or rental associated to the message are not
	 *                   found.
	 */
	public void saveMessage(MessageRequest messageRequest) throws Exception {

		Message message = new Message();

		Optional<User> author = userService.getUserById(messageRequest.getUser_id());
		if (author.isEmpty()) {
			throw new Exception("No author found");
		}
		message.setAuthor(author.get());

		Optional<Rental> rental = rentalService.getRentalById(messageRequest.getRental_id());
		if (rental.isEmpty()) {
			throw new Exception("No rental found");
		}
		message.setRental(rental.get());
		message.setMessage(messageRequest.getMessage());

		messageRepository.save(message);
	}
}