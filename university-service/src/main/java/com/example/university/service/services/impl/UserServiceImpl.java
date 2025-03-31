package com.example.university.service.services.impl;

import com.example.university.service.services.UserService;
import com.example.university.api.model.User;
import com.example.university.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {;
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // Check if user exists
        getUserById(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
} 