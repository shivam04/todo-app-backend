package com.shivam.todoapp.controller;

import com.shivam.todoapp.dtos.AuthResponseDTO;
import com.shivam.todoapp.dtos.AuthRequestDTO;;
import com.shivam.todoapp.service.AuthService;
import com.shivam.todoapp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.authenticateUser(authRequestDTO);
        if (authResponseDTO == null) {
            return new ResponseEntity<>("Invalid username password", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(authResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        String jwt = JWTUtil.extractAuthToken(authToken);
        SecurityContextHolder.getContext().setAuthentication(null);
        authService.unAuthenticateUser(jwt);
        return new ResponseEntity<>("LogOut Successful", HttpStatus.OK);
    }

}
