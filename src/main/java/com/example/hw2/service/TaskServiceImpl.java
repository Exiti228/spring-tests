package com.example.hw2.service;

import com.example.hw2.model.Task;
import com.example.hw2.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(Task task, Long taskId) {
        taskRepository.findById(taskId).ifPresent(tmpTask -> {
            mapping(task, tmpTask);
            taskRepository.save(tmpTask);
        });
    }

    public void mapping(Task inTask, Task outTask) {
        outTask.setCompleted(inTask.getCompleted());
        outTask.setDescription(inTask.getDescription());
        outTask.setTitle(inTask.getTitle());
        outTask.setDueDate(inTask.getDueDate());
    }
}
