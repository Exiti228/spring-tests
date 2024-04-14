package com.example.hw2;

import com.example.hw2.api.TaskController;
import com.example.hw2.model.Task;
import com.example.hw2.repository.TaskRepository;
import com.example.hw2.service.TaskService;
import com.example.hw2.service.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void getAllTasksShouldReturnAll() throws Exception {
        Mockito.when(this.taskService.getAllTasks()).thenReturn(getTasks());

        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getTaskByIdShouldReturnValidTask() throws Exception {
        Mockito.when(this.taskService.getTaskById(1L)).thenReturn(java.util.Optional.ofNullable(getTasks().get(0)));

        mvc.perform(get("/task/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("test1"))
                .andExpect(jsonPath("$.description").value("test1"))
                .andExpect(jsonPath("$.dueDate").value("test1"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void createTaskShouldReturnValidTask() throws Exception {
        Task t = getTasks().get(2);
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(t);
        mvc.perform(
                post("/tasks")
                        .content(objectMapper.writeValueAsString(t))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void deleteTaskShouldDeleteValidTask() throws Exception {
        mvc.perform(
                delete("/tasks/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void updateTaskShouldReturnValidTask() throws Exception {
        Task t = getTasks().get(2);
        mvc.perform(
                put("/task/{id}", 2)
                        .content(objectMapper.writeValueAsString(t))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private List<Task> getTasks() {
        Task task1 = new Task(1L, "test1", "test1", "test1", false);
        Task task2 = new Task(2L, "test2", "test2", "test2", true);
        Task task3 = new Task(3L, "test3", "test3", "test3", false);
        return List.of(task1, task2, task3);
    }
}
