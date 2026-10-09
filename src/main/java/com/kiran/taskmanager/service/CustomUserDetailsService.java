package com.kiran.taskmanager.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kiran.taskmanager.entity.User;
import com.kiran.taskmanager.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	 @Override
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {

	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() ->
	                        new UsernameNotFoundException("User not found: " + username));

	        return org.springframework.security.core.userdetails.User
	                .withUsername(user.getUsername())
	                .password(user.getPassword())
	                .roles(user.getRole().name())
	                .build();
	    }
}



//it tells Spring Security how to fetch a user from your database and in what format, so authentication and authorization work with your own User table.