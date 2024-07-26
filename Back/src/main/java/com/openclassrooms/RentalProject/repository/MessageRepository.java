package com.openclassrooms.RentalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalProject.model.entity.Message;

/**
 * Repository of the messages.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}