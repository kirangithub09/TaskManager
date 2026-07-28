package com.kiran.taskmanager.repository;

//to import list collection
import java.util.List;

//imports readymade toolbox (methods)
import org.springframework.data.jpa.repository.JpaRepository;

//imports @query annotation --> which allows to write our own db query
import org.springframework.data.jpa.repository.Query;

//imports @param annotation --> which connects java variables with query variables
import org.springframework.data.repository.query.Param;

//imports @Repository --> which tells spring this interface communicates with db
import org.springframework.stereotype.Repository;

//to import priority enum
import com.kiran.taskmanager.entity.Priority;

//to import status enum
import com.kiran.taskmanager.entity.Status;

//to import task entity
import com.kiran.taskmanager.entity.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task	, Long> {
	List<Task> findByUserId(Long userId);
	//List<task> --> one user can have multiple tasks 
	
	List<Task> findByUserIdAndStatus(Long userId, Status status);
	List<Task> findByUserIdAndPriority(Long userId, Priority priority);
	
	@Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.title LIKE %:keyword%")
	List<Task> searchByTitle(@Param("userId") Long userId, @Param("keyword") String keyword);
}
