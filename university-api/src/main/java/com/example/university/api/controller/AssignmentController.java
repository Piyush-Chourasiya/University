package com.example.university.api.controller;

import com.example.university.api.model.Assignment;
import java.util.List;

import com.example.university.api.wrapper.AssignmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
@RequestMapping("/api/assignments")
public interface AssignmentController {
    
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    ResponseEntity<Assignment> createAssignment(@RequestBody AssignmentRequest assignmentRequest);
    
    @PutMapping("/{id}")
    ResponseEntity<Assignment> updateAssignment(@PathVariable Integer id, @RequestBody Assignment assignment);
    
    @GetMapping("/{id}")
    ResponseEntity<Assignment> getAssignment(@PathVariable Integer id);
    
    @GetMapping
    ResponseEntity<List<Assignment>> getAllAssignments();
    
    @GetMapping("/course/{courseId}")
    ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable Integer courseId);
    
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAssignment(@PathVariable Integer id);
}
