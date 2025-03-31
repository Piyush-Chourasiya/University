package com.example.university.service.services;

import com.example.university.api.model.Course;
import com.example.university.api.model.User;

import java.util.List;
import java.util.Optional;


public interface CourseService {
   
    List<Course> findAll();
    
   
    List<Course> findAllByActive(Boolean active);
    
   
    Optional<Course> findById(Integer id);
    
   
    Course save(Course course);
    
    
    void delete(Integer id);
    
   
    Course enrollStudent(Course course, User student);
    
   
    Course unenrollStudent(Course course, User student);
    
  
    List<Course> findByInstructor(User instructor);
    
  
    List<Course> findBySemester(String semester);
} 