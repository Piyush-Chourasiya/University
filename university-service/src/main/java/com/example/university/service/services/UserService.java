package com.example.university.service.services;

import com.example.university.api.model.User;
import java.util.List;


public interface UserService {


    List<User> getAllUsers();


    User getUserById(Integer id);


    User createUser(User user);


    User updateUser(User user);


    void deleteUser(Integer id);
} 