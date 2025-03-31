package com.example.university.service.repository;

import com.example.university.api.model.Course;
import com.example.university.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    
    
    List<Course> findByIsActive(Boolean isActive);
    
   
    List<Course> findByCourseInstructor(User instructor);
    
  
    List<Course> findBySemester(String semester);
    
    
    Course findByCourseCode(String courseCode);
}
