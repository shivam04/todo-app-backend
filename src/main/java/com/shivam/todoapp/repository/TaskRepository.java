package com.shivam.todoapp.repository;

import com.shivam.todoapp.model.Task;
import com.shivam.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByUser(User user);
}
