package com.example.university.service.controllerimpl;

import com.example.university.api.controller.AssignmentController;
import com.example.university.api.model.Assignment;
import com.example.university.service.services.AssignmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignmentControllerImpl implements AssignmentController {
    
    @Autowired
    private  AssignmentService assignmentService;
    
    @Override
    public ResponseEntity<Assignment> createAssignment(Assignment assignment) {
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }
    
    @Override
    public ResponseEntity<Assignment> updateAssignment(Integer id, Assignment assignment) {
        Assignment updatedAssignment = assignmentService.updateAssignment(id, assignment);
        return ResponseEntity.ok(updatedAssignment);
    }
    
    @Override
    public ResponseEntity<Assignment> getAssignment(Integer id) {
        Assignment assignment = assignmentService.getAssignment(id);
        return ResponseEntity.ok(assignment);
    }
    
    @Override
    public ResponseEntity<List<Assignment>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }
    
    @Override
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(Integer courseId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.ok(assignments);
    }
    
    @Override
    public ResponseEntity<Void> deleteAssignment(Integer id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
