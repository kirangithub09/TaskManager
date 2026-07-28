package com.kiran.taskmanager.dto;

import com.kiran.taskmanager.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse {
	
	private Long id;
	private String username;
	private String email;
	private Role role;
}

//this is response dto
//therefore it doesn't contain sensitive feild such as password
