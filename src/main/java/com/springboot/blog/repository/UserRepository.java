package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        //Definition: Optional is a container object that may or may not contain a non-null value.
        // It helps to explicitly represent the absence of a value instead of using null

        Optional<User> findByEmail(String email);

        Optional<User> findByUsernameOrEmail(String username, String email);

        Optional<User> findByUsername(String username);

        Boolean existsByUsername(String username);

        Boolean existsByEmail(String email);




}
