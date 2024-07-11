package com.openclassrooms.RentalProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalProject.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

}
