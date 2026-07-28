package com.kiran.taskmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiran.taskmanager.entity.User;

@Repository


//JpaRepository contains inbuilt methods for database operations
//such as save(), delete(), findAll(), findById(), count(), existsById(), deleteById(), saveAll(), etc 
//so we don't have to write those methods manually just extend and import


public interface UserRepository extends JpaRepository<User, Long> {
	
	//JpaRepository<EntityType, PrimaryKeyType>
	
	Optional<User> findByUsername(String username);
	//Optional<User> is a returnType it either returns one user or no user
	
	Optional<User> findByEmail(String email);
	//findByEmail --> works as sql query like select * -> from users -> where email = ?
	
	boolean existsByUsername(String username);
	//returns true or false (useful during registration)
	//spring internally checks if a matching row exists
	
	boolean existsByEmail(String email);
	
}
