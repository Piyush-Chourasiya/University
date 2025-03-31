package com.example.university.api.controller;

import com.example.university.api.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/courses")
public interface CourseController {
    
 
    @GetMapping
    ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) Boolean active);
    
   
    @GetMapping("/{id}")
    ResponseEntity<Course> getCourseById(@PathVariable Integer id);
    
  
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    ResponseEntity<Course> createCourse(@RequestBody Course course);
    
    
   
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCourse(@PathVariable Integer id);
    
   
    @PostMapping("/{courseId}/enroll/{studentId}")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<Course> enrollStudent(@PathVariable Integer courseId, @PathVariable Integer studentId);
    
  
    @DeleteMapping("/{courseId}/enroll/{studentId}")
    ResponseEntity<Course> unenrollStudent(@PathVariable Integer courseId, @PathVariable Integer studentId);
    
    
   
    @GetMapping("/instructor/{instructorId}")
    ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Integer instructorId);
    
  
    @GetMapping("/semester/{semester}")
    ResponseEntity<List<Course>> getCoursesBySemester(@PathVariable String semester);
    
  
    @PatchMapping("/{id}/activate")
    ResponseEntity<Course> activateCourse(@PathVariable Integer id);
    
  
    @PatchMapping("/{id}/deactivate")
    ResponseEntity<Course> deactivateCourse(@PathVariable Integer id);
}
