package com.shivam.todoapp.dtos.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteTaskResponseDTO {
    private String message;
}
