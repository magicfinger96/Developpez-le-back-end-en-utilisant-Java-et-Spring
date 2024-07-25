package com.openclassrooms.RentalProject.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity of a user.
 */
@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String email;
	private String name;
	private String password;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date creationDate;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updateDate;
}
