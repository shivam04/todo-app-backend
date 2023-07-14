package com.shivam.todoapp.dtos.task;

import lombok.Getter;

import java.util.Date;

@Getter
public class CreateTaskRequestDTO {
    private String name;
    private Date dueDate;
    private Boolean completed;
}
