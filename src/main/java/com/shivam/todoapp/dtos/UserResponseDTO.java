package com.shivam.todoapp.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private String name;
}
