package com.shivam.todoapp.dtos.task;

import lombok.Getter;

import java.util.Date;

@Getter
public class UpdateTaskRequestDTO {
    private String name;
    private Date dueDate;
    private Boolean completed;
    private Long id;
}
