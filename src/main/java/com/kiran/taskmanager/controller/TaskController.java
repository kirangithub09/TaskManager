package com.kiran.taskmanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiran.taskmanager.dto.TaskRequest;
import com.kiran.taskmanager.dto.TaskResponse;
import com.kiran.taskmanager.entity.Priority;
import com.kiran.taskmanager.entity.Status;
import com.kiran.taskmanager.entity.Task;
import com.kiran.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
	
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	private static final Long CURRENT_USER_ID = 1L;
	
	//=========================CREATE TASK========================
	@PostMapping
	public ResponseEntity<TaskResponse> createTask (@RequestBody @Valid TaskRequest request){
		TaskResponse task = taskService.createTask(request, CURRENT_USER_ID);
		return ResponseEntity.status(201).body(task);
		}
	
	//=======================GET ALL TASKS(WITH OPTIONAL FILTERS)=================
	@GetMapping
	public ResponseEntity<List<TaskResponse>> getTasks(
			@RequestParam(required = false)String status,
			@RequestParam(required = false)String priority,
			@RequestParam(required = false)String search
			){
		if(status != null) {
			return ResponseEntity.ok(taskService.getTasksByStatus(Status.valueOf(status.toUpperCase()), CURRENT_USER_ID));
		}
		
		if(priority != null) {
			return ResponseEntity.ok(taskService.getTasksByPriority(Priority.valueOf(priority.toUpperCase()), CURRENT_USER_ID));
		}
		
		if(search != null) {
			return ResponseEntity.ok(taskService.searchTasks(search, CURRENT_USER_ID));
		}
		
		return ResponseEntity.ok(taskService.getAllTasks(CURRENT_USER_ID));
	}
	
	
	//=========================GET SINGLE TASK==================================
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponse> getTask(@PathVariable Long id){
		return ResponseEntity.ok(taskService.getTaskById(id, CURRENT_USER_ID));
	}
	
	//==========================UPDATE TASK======================================
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponse> updateTask(
			@PathVariable Long id,
			@RequestBody @Valid TaskRequest request
			){
		return ResponseEntity.ok(taskService.updateTask(id, request, CURRENT_USER_ID));
	}
	
	
	//============================DELETE TASK===================================
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id){
		taskService.deleteTask(id, CURRENT_USER_ID);
		return ResponseEntity.noContent().build();
	}
	

}
