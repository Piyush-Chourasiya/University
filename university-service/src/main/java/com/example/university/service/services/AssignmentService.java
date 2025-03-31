package com.example.university.service.services;

import com.example.university.api.model.Assignment;
import java.util.List;

public interface AssignmentService {
    
    Assignment createAssignment(Assignment assignment);
    
    Assignment updateAssignment(Integer id, Assignment assignment);
    
    Assignment getAssignment(Integer id);
    
    List<Assignment> getAllAssignments();
    
    List<Assignment> getAssignmentsByCourse(Integer courseId);
    
    void deleteAssignment(Integer id);
    
    boolean assignmentExists(Integer id);
}
