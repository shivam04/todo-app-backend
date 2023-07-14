package com.shivam.todoapp.repository;

import com.shivam.todoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByUserName(String userName);
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByUserNameAndPassword(String userName, String password);
}
