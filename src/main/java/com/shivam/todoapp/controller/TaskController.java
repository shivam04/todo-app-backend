package com.shivam.todoapp.controller;

import com.shivam.todoapp.dtos.InvalidAuthResponseDTO;
import com.shivam.todoapp.dtos.task.CreateTaskRequestDTO;
import com.shivam.todoapp.dtos.task.UpdateTaskRequestDTO;
import com.shivam.todoapp.dtos.task.DeleteTaskResponseDTO;
import com.shivam.todoapp.dtos.task.DeleteTaskRequestDTO;
import com.shivam.todoapp.dtos.task.TaskResponseDTO;
import com.shivam.todoapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/task")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequestDTO createTaskRequestDTO,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        TaskResponseDTO taskResponseDTO = taskService.createTask(createTaskRequestDTO, authToken);
        return new ResponseEntity<>(taskResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequestDTO updateTaskRequestDTO,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        TaskResponseDTO taskResponseDTO = taskService.updateTask(updateTaskRequestDTO, authToken);
        if (taskResponseDTO == null) {
            return invalidAuthResponse();
        }
        return new ResponseEntity<>(taskResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestBody DeleteTaskRequestDTO deleteTaskRequestDTO,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        DeleteTaskResponseDTO deleteTask = taskService.deleteTask(deleteTaskRequestDTO, authToken);
        if (deleteTask == null) {
            return invalidAuthResponse();
        }
        return new ResponseEntity<>(deleteTask, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readTask(@PathVariable Long id,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        TaskResponseDTO taskResponseDTO = taskService.readTask(id, authToken);
        if (taskResponseDTO == null) {
            return invalidAuthResponse();
        }
        return new ResponseEntity<>(taskResponseDTO, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTasks(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        List<TaskResponseDTO> taskResponseDTOList = taskService.getAllTasks(authToken);
        return new ResponseEntity<>(taskResponseDTOList, HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<?> invalidAuthResponse() {
        return new ResponseEntity<>(InvalidAuthResponseDTO.builder()
                .message("Invalid Data")
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
