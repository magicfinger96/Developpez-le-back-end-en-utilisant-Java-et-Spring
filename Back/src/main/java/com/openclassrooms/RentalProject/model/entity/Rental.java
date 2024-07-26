package com.openclassrooms.RentalProject.model.entity;

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
 * Entity of a rental.
 */
@Entity
@Table(name = "rental")
@Data
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private String name;
	private float surface;
	private float price;
	private String picture;
	private String description;

	@OneToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date creationDate;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updateDate;
}
