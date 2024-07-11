package com.openclassrooms.RentalProject.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "message")
@Data
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JoinColumn(name="rental_id")
	private Rental rental;
	
	@JoinColumn(name="user_id")
	private User author;
	
	private String message;
	
	@Column(name = "created_at")
	private Date creationDate;
	
	@Column(name = "updated_at")
	private Date updateDate;
}