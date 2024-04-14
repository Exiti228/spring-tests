package com.example.hw2.service;

import com.example.hw2.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    void createTask(Task task);

    void deleteTask(Long id);

    void updateTask(Task task, Long taskId);
}
