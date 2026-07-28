package com.kiran.taskmanager.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name ="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	 @Enumerated(EnumType.STRING)
	 @Column(nullable = false)
     private Role role = Role.USER;
	 
	 @Column(name = "created_at")
	 private LocalDateTime createdAt;
	 
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<Task> tasks;

	 @PrePersist
	 public void prePersist() {
	     this.createdAt = LocalDateTime.now();
	 }
	
	
}
