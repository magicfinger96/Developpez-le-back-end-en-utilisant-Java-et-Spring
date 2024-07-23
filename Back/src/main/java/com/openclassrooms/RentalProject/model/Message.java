package com.openclassrooms.RentalProject.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity of a Message.
 */
@Entity
@Table(name = "message")
@Data
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * Rental for which the message is about.
	 */
	@OneToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User author;

	private String message;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date creationDate;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updateDate;
}