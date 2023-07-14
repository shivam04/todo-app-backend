package com.shivam.todoapp.dtos.task;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String name;
    private Date dueDate;
    private Boolean completed;
}
