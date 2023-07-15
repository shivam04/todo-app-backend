package com.shivam.todoapp.controller;

import com.shivam.todoapp.dtos.RegisterUserRequestDTO;
import com.shivam.todoapp.dtos.UserResponseDTO;
import com.shivam.todoapp.helper.AuthHelper;
import com.shivam.todoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final AuthHelper authHelper;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        UserResponseDTO userResponseDTO = userService.registerUser(registerUserRequestDTO);
        if (userResponseDTO == null) {
            return new ResponseEntity<>("User Not Created", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @PathVariable("username") String username) {
        if (!authHelper.isValid(username, token)) {
            return new ResponseEntity<>("User not Authenticated", HttpStatus.UNAUTHORIZED);
        }
        UserResponseDTO userResponseDTO = userService.getUserDetails(username);
        if (userResponseDTO == null) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
