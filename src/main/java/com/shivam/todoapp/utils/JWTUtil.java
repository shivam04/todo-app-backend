package com.shivam.todoapp.utils;

import com.shivam.todoapp.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.tokenValidity}")
    private long TOKEN_VALIDITY;

    private final RedisTemplate<String, Token> redisTemplate;

    @Autowired
    public JWTUtil(RedisTemplate<String, Token> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static String extractAuthToken(String authToken) {
        if (StringUtils.isEmpty(authToken) || !authToken.startsWith("Bearer ")) {
            return null;
        }
        return authToken.split(" ")[1];
    }

    public String parseToken(String token) {
        try {
            String userName = getClaimForToken(token, Claims::getSubject);
            return userName;
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public boolean validateToken(String jwt, UserDetails user) {
        return this.parseToken(jwt).equalsIgnoreCase(user.getUsername()) && !isTokenExpired(jwt)
                && isValidInDataStore(jwt);
    }

    private boolean isValidInDataStore(String jwt) {
        return redisTemplate.hasKey(jwt) &&
                redisTemplate.opsForValue().get(jwt).isValid();
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    private <T> T getClaimForToken(String token, Function<Claims, T> claimResolver) {
        Claims body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claimResolver.apply(body);
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());
        String jwt = Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        redisTemplate.opsForValue().set(jwt, new Token(jwt, true, userDetails.getUsername()));
        redisTemplate.expireAt(jwt, new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000));
        return jwt;
    }

    public void removeToken(String jwt) {
        redisTemplate.delete(jwt);
    }
}
