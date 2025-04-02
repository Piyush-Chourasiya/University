package com.example.university.service.services.impl;

import com.example.university.service.mail.UniversityMailSender;
import com.example.university.service.services.UserService;
import com.example.university.api.model.User;
import com.example.university.service.repository.UserRepository;
import com.example.university.service.security.RoleCacheFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UniversityMailSender universityMailSender;

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
    public User createUser(User user) {
       try{
        if(user == null)
        {
            logger.error("User can not be null");
            throw new Exception("User can not be null");
        }
        if(User.UserRole.STUDENT.equals(user.getRole()) || Objects.equals(user.getRole().toString(), "PROFESSOR" ))
        {
            if(universityMailSender.sendUsernameAndPasswordNotification(user))
            {
                logger.info("Username and Password send through mail successfully.......");
            }
            else {
                logger.info("Mail not send.......");
            }
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return userRepository.save(user);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
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