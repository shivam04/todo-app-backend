package com.shivam.todoapp.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
public class User extends BaseModel {
    private String userName;
    private String email;
    private String password;
    private String name;
}
