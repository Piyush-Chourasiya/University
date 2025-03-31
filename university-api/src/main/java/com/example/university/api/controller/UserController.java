package com.example.university.api.controller;

import com.example.university.api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController {


    @GetMapping
    ResponseEntity<List<User>> getAllUsers();


    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id);


    @PostMapping
    ResponseEntity<User> createUser(@RequestBody User user);


    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user);


    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id);
} 