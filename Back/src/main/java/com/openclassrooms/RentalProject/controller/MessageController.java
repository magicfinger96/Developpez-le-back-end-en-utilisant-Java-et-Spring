package com.openclassrooms.RentalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.RentalProject.model.dto.MessageResponse;
import com.openclassrooms.RentalProject.model.request.MessageRequest;
import com.openclassrooms.RentalProject.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

/**
 * Handles the end points related to the message.
 */
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class MessageController {

	@Autowired
	private MessageService messageService;

	/**
	 * Create - Add a new message.
	 *
	 * @param message The message to save.
	 * @return a ResponseEntity containing a MessageResponse if it succeeded,
	 *         otherwise returns an error ResponseEntity.
	 */
	@Operation(summary = "Create a message")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Created the message", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "User or rental, associated with the message, not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = @Content),
			@ApiResponse(responseCode = "401", description = "JWT is wrong or missing", content = @Content) })
	@PostMapping("/api/messages")
	public ResponseEntity<MessageResponse> createMessage(@Valid @RequestBody MessageRequest message) {

		try {
			messageService.saveMessage(message);
		} catch (Exception e) {
			System.out.println("Error while creating a message: " + e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		MessageResponse response = new MessageResponse("Message send with success");
		return ResponseEntity.ok(response);
	}
}
