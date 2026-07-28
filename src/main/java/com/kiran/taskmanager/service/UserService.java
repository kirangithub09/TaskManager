package com.kiran.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiran.taskmanager.dto.LoginRequest;
import com.kiran.taskmanager.dto.RegisterRequest;
import com.kiran.taskmanager.dto.UserResponse;
import com.kiran.taskmanager.entity.User;
import com.kiran.taskmanager.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository= userRepository;
		this.passwordEncoder= passwordEncoder;
	}
	
	
	//============================REGISTER============================
	
	public UserResponse register(RegisterRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username already taken!");
		}
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered!");
		}
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		User savedUser= userRepository.save(user);
		
		return new UserResponse(
				savedUser.getId(),
				savedUser.getUsername(),
				savedUser.getEmail(),
				savedUser.getRole()
				);
	}
	
	//=====================LOGIN=========================
	
	public UserResponse login(LoginRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
		.orElseThrow(()-> new RuntimeException("Invalid username or password"));
		
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}
		
		return new UserResponse(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getRole()
				);
	}
}
