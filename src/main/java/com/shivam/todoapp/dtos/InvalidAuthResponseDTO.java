package com.shivam.todoapp.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidAuthResponseDTO {
    private String message;
}
