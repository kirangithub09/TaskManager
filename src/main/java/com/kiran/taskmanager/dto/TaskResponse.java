package com.kiran.taskmanager.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kiran.taskmanager.entity.Priority;
import com.kiran.taskmanager.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

	 private Long id;
	 private String title;
	 private String description;
	 private Status status;
	 private Priority priority;
	 private LocalDate dueDate;
	 private LocalDateTime createdAt;
	 private LocalDateTime updatedAt;
}
