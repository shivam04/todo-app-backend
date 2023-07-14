package com.shivam.todoapp.service;

import com.shivam.todoapp.dtos.RegisterUserRequestDTO;
import com.shivam.todoapp.dtos.UserResponseDTO;
import com.shivam.todoapp.model.User;
import com.shivam.todoapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO) {
        User user = userRepository.findUserByUserName(registerUserRequestDTO.getUserName());
        if (user != null) {
            return null;
        }
        user = new User();
        user.setUserName(registerUserRequestDTO.getUserName());
        user.setName(registerUserRequestDTO.getName());
        user.setEmail(registerUserRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserRequestDTO.getPassword()));
        return generateResponse(userRepository.save(user));
    }

    public UserResponseDTO getUserDetails(String username) {
        try {
            User user = userRepository.findUserByUserName(username);
            return generateResponse(user);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private UserResponseDTO generateResponse(User user) {
        if (user == null)
            return null;
        return UserResponseDTO
                .builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
