package com.kiran.taskmanager.dto;

import java.time.LocalDate;

import com.kiran.taskmanager.entity.Priority;
import com.kiran.taskmanager.entity.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

	@NotBlank(message = "Task title is required")
	@Size(max=255, message = "Title cannot exceed 255 characters")
	private String title;
	
	@Size(max = 1000, message = "Description cannot exceed 1000 characters")
	private String description;
	
	private Status status= Status.TODO;
	private Priority priority = Priority.MEDIUM;
	
	private LocalDate dueDate;
}
