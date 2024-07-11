package com.openclassrooms.RentalProject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.RentalProject.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
