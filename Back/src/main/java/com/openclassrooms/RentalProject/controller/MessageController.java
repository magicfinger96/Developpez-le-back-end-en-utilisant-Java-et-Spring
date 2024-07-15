package com.openclassrooms.RentalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.model.Message;
import com.openclassrooms.RentalProject.service.MessageService;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;
    
	/**
	 * Create - Add a new message
	 * @param message An object Message
	 * @return The Message object saved
	 */
	@PostMapping("/message")
	public Message createEmployee(@RequestBody Message message) {
		return messageService.saveMessage(message);
	}
}
