package com.kiran.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiran.taskmanager.dto.TaskRequest;
import com.kiran.taskmanager.entity.Priority;
import com.kiran.taskmanager.entity.Status;
import com.kiran.taskmanager.entity.Task;
import com.kiran.taskmanager.entity.User;
import com.kiran.taskmanager.repository.TaskRepository;
import com.kiran.taskmanager.repository.UserRepository;

@Service
@Transactional
public class TaskService {

	private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    
    //========================CREATE=============================
    public Task createTask(TaskRequest request, Long userId) {
    	
    	User user = userRepository.findById(userId)
    	.orElseThrow(()-> new RuntimeException("User not found with id: "+ userId));
    	
    	Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());       // defaults to TODO if not sent
        task.setPriority(request.getPriority());   // defaults to MEDIUM if not sent
        task.setDueDate(request.getDueDate()); 
        
        task.setUser(user);
        
        return taskRepository.save(task);
    	
    }
    
    //====================GET ALL TASKS FOR A USER=======================
    
    public List<Task> getAllTasks(Long userId){
    	return taskRepository.findByUserId(userId);
    }
    
    
    //===================GET SINGLE TAKS=============================
    public Task getTaskById(Long taskId, Long userId) {
    	Task task = taskRepository.findById(taskId)
    	.orElseThrow(()-> new RuntimeException("Task not found with id: "+taskId));	
    	
    	if(!task.getUser().getId().equals(userId)) {
    		throw new RuntimeException("You are not authorize to see this task");
    	}
    	return task;
    }
    
    //=====================FILTER BY STATUS=========================
    public List<Task> getTasksByStatus(Status status ,Long userId){
    	return taskRepository.findByUserIdAndStatus(userId, status);
    }
    
    //=====================FILTER BY PRIORITY===========================
    public List<Task> getTasksByPriority(Priority priority, Long userId){
    	return taskRepository.findByUserIdAndPriority(userId, priority);
    }
    
    //=====================SEARCH BY TITLE==========================
    public List<Task> searchTasks(String keyword, Long userId){
    	return taskRepository.searchByTitle(userId, keyword);
    }
    
    
    //=============================UPDATE============================
    public Task updateTask(Long taskId, TaskRequest request, Long userId) {
    	Task task = taskRepository.findById(taskId)
    	.orElseThrow(()-> new RuntimeException("Task not found with id:"+ taskId));
    	
    	if(!task.getUser().getId().equals(userId)) {
    		throw new RuntimeException("You are not authorized to update this task");
    	}
    	
    	task.setTitle(request.getTitle());
    	task.setDescription(request.getDescription());
    	task.setStatus(request.getStatus());
    	task.setPriority(request.getPriority());
    	task.setDueDate(request.getDueDate());
    	
    	return taskRepository.save(task);
    }
    
    
    //=========================DELETE==============================
    public void deleteTask(Long taskId, Long userId) {
    	Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    	
    	 if (!task.getUser().getId().equals(userId)) {
             throw new RuntimeException("You are not authorized to delete this task");
         }
    	 
    	 taskRepository.deleteById(taskId);
    	 
    }
    

}
