package com.openclassrooms.RentalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.DTO.MessageRequest;
import com.openclassrooms.RentalProject.DTO.MessageResponse;
import com.openclassrooms.RentalProject.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;

	/**
	 * Create - Add a new message
	 * 
	 * @param message An object ResponseEntity<MessageResponse>
	 * @return The ResponseEntity with a MessageResponse if succeeded
	 */
	@PostMapping("/api/messages")
	public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest message) {

		try {
			messageService.saveMessage(message);
		} catch (Exception e) {
			System.out.print("Error while creating a message: " + e);
			return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
		}

		MessageResponse response = new MessageResponse();
		response.setMessage("Message send with success");

		return ResponseEntity.ok(response);
	}
}
