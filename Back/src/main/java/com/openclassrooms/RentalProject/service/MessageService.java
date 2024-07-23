package com.openclassrooms.RentalProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.RentalProject.model.Message;
import com.openclassrooms.RentalProject.repository.MessageRepository;
 
@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	public Message saveMessage(Message message) {
		return messageRepository.save(message);		
	}
}