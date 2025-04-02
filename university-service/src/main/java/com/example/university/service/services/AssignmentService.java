package com.example.university.service.services;

import com.example.university.api.model.Assignment;
import com.example.university.api.wrapper.AssignmentRequest;

import java.util.List;

public interface AssignmentService {
    
    Assignment createAssignment(AssignmentRequest assignmentRequest);
    
    Assignment updateAssignment(Integer id, Assignment assignment);
    
    Assignment getAssignment(Integer id);
    
    List<Assignment> getAllAssignments();
    
    List<Assignment> getAssignmentsByCourse(Integer courseId);
    
    void deleteAssignment(Integer id);
    
    boolean assignmentExists(Integer id);
}
