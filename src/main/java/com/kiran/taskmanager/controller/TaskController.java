package com.kiran.taskmanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.kiran.taskmanager.entity.User;
import com.kiran.taskmanager.repository.UserRepository;
import com.kiran.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(
            TaskService taskService,
            UserRepository userRepository) {

        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    // ========================= GET CURRENT USER ID =========================

    private Long getCurrentUserId(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return user.getId();
    }

    // ========================= CREATE TASK ================================

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody @Valid TaskRequest request,
            Authentication authentication) {

        Long currentUserId = getCurrentUserId(authentication);

        TaskResponse task =
                taskService.createTask(request, currentUserId);

        return ResponseEntity.status(201).body(task);
    }

    // ======================= GET ALL TASKS ================================

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String search,
            Authentication authentication) {

        Long currentUserId = getCurrentUserId(authentication);

        if (status != null) {

            return ResponseEntity.ok(
                    taskService.getTasksByStatus(
                            Status.valueOf(status.toUpperCase()),
                            currentUserId));
        }

        if (priority != null) {

            return ResponseEntity.ok(
                    taskService.getTasksByPriority(
                            Priority.valueOf(priority.toUpperCase()),
                            currentUserId));
        }

        if (search != null) {

            return ResponseEntity.ok(
                    taskService.searchTasks(
                            search,
                            currentUserId));
        }

        return ResponseEntity.ok(
                taskService.getAllTasks(currentUserId));
    }

    // ========================= GET SINGLE TASK ============================

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long id,
            Authentication authentication) {

        Long currentUserId = getCurrentUserId(authentication);

        return ResponseEntity.ok(
                taskService.getTaskById(
                        id,
                        currentUserId));
    }

    // ========================== UPDATE TASK ===============================

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskRequest request,
            Authentication authentication) {

        Long currentUserId = getCurrentUserId(authentication);

        return ResponseEntity.ok(
                taskService.updateTask(
                        id,
                        request,
                        currentUserId));
    }

    // ========================== DELETE TASK ===============================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        Long currentUserId = getCurrentUserId(authentication);

        taskService.deleteTask(id, currentUserId);

        return ResponseEntity.noContent().build();
    }
}