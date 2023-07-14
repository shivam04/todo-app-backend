package com.shivam.todoapp.dtos;

import lombok.Getter;

@Getter
public class RegisterUserRequestDTO {
    private String userName;
    private String email;
    private String password;
    private String name;
}
