package com.example.university.service.services.impl;

import com.example.university.api.model.Assignment;
import com.example.university.api.model.Course;
import com.example.university.api.model.User;
import com.example.university.service.repository.AssignmentRepository;
import com.example.university.service.repository.CourseRepository;
import com.example.university.service.repository.UserRepository;
import com.example.university.service.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    
    private static final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private com.example.university.service.mail.UniversityMailSender universityMailSender;
    
    @Override
    @Transactional
    public Assignment createAssignment(Assignment assignment) {
        logger.info("Creating new assignment for course ID: {}", 
                assignment.getCourse() != null ? assignment.getCourse().getId() : "null");
        
        assignment.setCreatedAt(LocalDateTime.now());
        assignment.setUpdatedAt(LocalDateTime.now());
        assignment.setDueDate(LocalDateTime.now().plusDays(14));
        
        logger.info("Assignment details - Title: {}, Description: {}, Due date: {}", 
                assignment.getTitle(), 
                assignment.getDescription(), 
                assignment.getDueDate());
        
        Assignment savedAssignment = assignmentRepository.save(assignment);
        logger.info("Assignment saved with ID: {}", savedAssignment.getId());
        
        
        Course course = assignment.getCourse();
        logger.info("Course: {}", course);

        if(course==null)
        {
            logger.info("Course is null");
        }
        
        // Get all students enrolled in this course
        List<User> enrolledStudents = userRepository.findByCourseAndRole(course.getId(), "STUDENT");
        logger.info("Found {} students enrolled in course ID: {}", enrolledStudents.size(), course.getId());
        
        // Send email notification
        universityMailSender.sendNewAssignmentNotification(enrolledStudents, course, List.of(savedAssignment));
        logger.info("Email notifications sent successfully to {} students", enrolledStudents.size());
        return savedAssignment;
    }
    
    @Override
    @Transactional
    public Assignment updateAssignment(Integer id, Assignment assignment) {
        assignment.setId(id);
        return assignmentRepository.save(assignment);
    }
    
    @Override
    public Assignment getAssignment(Integer id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        return assignment.orElse(null);
    }
    
    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    
    @Override
    public List<Assignment> getAssignmentsByCourse(Integer courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(assignmentRepository::findByCourse).orElse(null);
    }
    
    @Override
    @Transactional
    public void deleteAssignment(Integer id) {
        assignmentRepository.deleteById(id);
    }
    
    @Override
    public boolean assignmentExists(Integer id) {
        return assignmentRepository.existsById(id);
    }
}
