package com.shivam.todoapp.controller;

import com.shivam.todoapp.dtos.HelloResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping(value = "")
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>(HelloResponseDTO.builder().message("HelloWorld").build(), HttpStatus.OK);
    }
}
