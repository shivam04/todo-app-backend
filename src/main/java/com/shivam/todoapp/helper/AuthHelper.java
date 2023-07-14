package com.shivam.todoapp.helper;

import com.shivam.todoapp.utils.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthHelper {
    private final JWTUtil jwtUtil;
    public boolean isValid(String username, String token) {
        String jwt = JWTUtil.extractAuthToken(token);
        String jwtUserName = jwtUtil.parseToken(jwt);
        return username.equals(jwtUserName);
    }
}
