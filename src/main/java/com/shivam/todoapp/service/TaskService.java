package com.shivam.todoapp.service;

import com.shivam.todoapp.dtos.task.CreateTaskRequestDTO;
import com.shivam.todoapp.dtos.task.UpdateTaskRequestDTO;
import com.shivam.todoapp.dtos.task.DeleteTaskResponseDTO;
import com.shivam.todoapp.dtos.task.DeleteTaskRequestDTO;
import com.shivam.todoapp.dtos.task.TaskResponseDTO;
import com.shivam.todoapp.model.Task;
import com.shivam.todoapp.model.User;
import com.shivam.todoapp.repository.TaskRepository;
import com.shivam.todoapp.repository.UserRepository;
import com.shivam.todoapp.utils.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {
    private final TaskRepository taskRepository;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public TaskResponseDTO createTask(CreateTaskRequestDTO createTaskRequestDTO,
                String token) {
            User user = getUserDetails(token);
            Task task = new Task();
            task.setCompleted(false);
            task.setName(createTaskRequestDTO.getName());
            task.setDueDate(createTaskRequestDTO.getDueDate());
            task.setUser(user);
            return generateResponse(taskRepository.save(task));
    }

    public TaskResponseDTO updateTask(UpdateTaskRequestDTO updateTaskRequestDTO, String authToken) {
        User user = getUserDetails(authToken);
        Task task = taskRepository.getReferenceById(updateTaskRequestDTO.getId());
        if (task==null || !isValidAuth(user, task)) {
            return null;
        }
        task.setName(updateTaskRequestDTO.getName() == null ? task.getName() : updateTaskRequestDTO.getName());
        task.setDueDate(updateTaskRequestDTO.getDueDate() == null ? task.getDueDate() : updateTaskRequestDTO.getDueDate());
        task.setCompleted(updateTaskRequestDTO.getCompleted() == null ? task.getCompleted() : updateTaskRequestDTO.getCompleted());
        return generateResponse(taskRepository.save(task));
    }

    public DeleteTaskResponseDTO deleteTask(DeleteTaskRequestDTO deleteTaskRequestDTO, String authToken) {
        User user = getUserDetails(authToken);
        Task task = taskRepository.getReferenceById(deleteTaskRequestDTO.getId());
        if (task==null || !isValidAuth(user, task)) {
            return null;
        }
        taskRepository.deleteById(deleteTaskRequestDTO.getId());
        return DeleteTaskResponseDTO
                .builder().message("Delete Successfully").build();
    }

    private boolean isValidAuth(User user, Task task) {
        return user.getUserName().equals(task.getUser().getUserName());
    }

    public TaskResponseDTO readTask(Long id, String authToken) {
        User user = getUserDetails(authToken);
        Task task = taskRepository.getReferenceById(id);
        if (task == null || !isValidAuth(user, task)) {
            return null;
        }
        return generateResponse(task);
    }

    public List<TaskResponseDTO> getAllTasks(String token) {
        User user = getUserDetails(token);
        List<Task> tasks = taskRepository.findTasksByUser(user);
        List<TaskResponseDTO> taskResponseDTOList = new ArrayList<>();
        for (Task task: tasks) {
            taskResponseDTOList.add(generateResponse(task));
        }
        return taskResponseDTOList;
    }

    private User getUserDetails(String token) {
        String jwt = JWTUtil.extractAuthToken(token);
        String userName = jwtUtil.parseToken(jwt);
        return userRepository.findUserByUserName(userName);
    }

    private TaskResponseDTO generateResponse(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .dueDate(task.getDueDate())
                .completed(task.getCompleted())
                .build();
    }
}
