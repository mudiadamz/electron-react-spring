package com.adam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adam.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
