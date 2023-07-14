package com.shivam.todoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Task extends BaseModel {
    private String name;
    private Date dueDate;
    private Boolean completed;
    @ManyToOne
    private User user;
}
