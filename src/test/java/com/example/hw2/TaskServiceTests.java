package com.example.hw2;

import com.example.hw2.model.Task;
import com.example.hw2.repository.TaskRepository;
import com.example.hw2.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    public void setup(){
        task = Task.builder()
                .id(1L)
                .title("test")
                .description("test")
                .dueDate("test")
                .completed(false)
                .build();
    }

    @Test
    public void createTaskShouldWork(){
        given(taskRepository.save(task)).willReturn(task);
        taskService.createTask(task);
    }

    @Test
    public void getAllTasksShouldWork(){
        Task task2 = Task.builder()
                .id(2L)
                .title("test2")
                .description("test2")
                .dueDate("test2")
                .completed(false)
                .build();
        given(taskRepository.findAll()).willReturn(List.of(task, task2));
        List<Task> tasks = taskService.getAllTasks();
        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(2);
    }

    @Test
    public void getTaskByIdShouldWork(){
        given(taskRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(task));
        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertThat(foundTask).isPresent();
    }

    @Test
    public void updateTaskByIdShouldWork(){
        given(taskRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(task));
        given(taskRepository.save(task)).willReturn(task);
        taskService.updateTask(task, 1L);
    }

    @Test
    public void deleteTaskByIdShouldWork(){
        taskRepository.deleteById(1L);
    }
}
