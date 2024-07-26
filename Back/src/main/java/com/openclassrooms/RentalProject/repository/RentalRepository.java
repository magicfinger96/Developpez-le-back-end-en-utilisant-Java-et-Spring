package com.openclassrooms.RentalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalProject.model.entity.Rental;

/**
 * Repository of the rentals.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}