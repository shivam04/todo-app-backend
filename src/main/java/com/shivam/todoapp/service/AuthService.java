package com.shivam.todoapp.service;

import com.shivam.todoapp.dtos.AuthResponseDTO;
import com.shivam.todoapp.dtos.AuthRequestDTO;
import com.shivam.todoapp.dtos.UserResponseDTO;
import com.shivam.todoapp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponseDTO authenticateUser(AuthRequestDTO authRequestDTO) throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUserName(), authRequestDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        } catch (DisabledException disabledException) {
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUserName());

        final String jwt = jwtUtil.generateToken(userDetails);
        return AuthResponseDTO
                .builder()
                .userResponseDTO(UserResponseDTO
                        .builder()
                        .userName(userDetails.getUsername())
                        .build())
                .jwtToken(jwt)
                .build();
    }

    public void unAuthenticateUser(String jwt) {
        jwtUtil.removeToken(jwt);
    }
}
