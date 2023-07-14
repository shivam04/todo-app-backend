package com.shivam.todoapp.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HelloResponseDTO {
    private String message;
}
