package com.shivam.todoapp.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDTO {
    private UserResponseDTO userResponseDTO;
    private String jwtToken;
}
