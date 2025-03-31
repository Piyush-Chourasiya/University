package com.example.university.service.controllerimpl;

import com.example.university.api.controller.UserController;
import com.example.university.api.model.User;
import com.example.university.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
   // @PreAuthorize("hasAuthority('ROLE_PROFESSOR')")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(User user) {
        logger.info("Request received to create user: {}", user);
        User createdUser = userService.createUser(user);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> updateUser(Integer id, User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 