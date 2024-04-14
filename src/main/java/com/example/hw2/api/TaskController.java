package com.example.hw2.api;

import com.example.hw2.model.Task;
import com.example.hw2.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        }
        catch (Exception e) {
            log.error("Error while GET /tasks: " + e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id).orElse(null);
            return ResponseEntity.status(HttpStatus.OK).body(task);
        }
        catch (Exception e) {
            log.error("Error while GET /task/{id}: " + e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch (Exception e) {
            log.error("Error while POST /tasks: " + e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Void> updateTask(@RequestBody Task task, @PathVariable Long id) {
        try {
            taskService.updateTask(task, id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch (Exception e) {
            log.error("Error while PUT /task/{id}: " + e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        catch (Exception e) {
            log.error("Error while DELETE /tasks/{id}: " + e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
