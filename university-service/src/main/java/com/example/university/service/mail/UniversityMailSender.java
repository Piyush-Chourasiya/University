package com.example.university.service.mail;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.university.api.model.User;
import com.example.university.api.model.Course;
import com.example.university.api.model.Assignment;


@Component
public class UniversityMailSender {
    
    private static final Logger logger = LoggerFactory.getLogger(UniversityMailSender.class);
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public boolean sendNewAssignmentNotification(List<User> students, Course course, List<Assignment> newAssignments) {
        logger.info("Starting to send assignment notifications for course: {} with {} assignments to {} students", 
        course.getCourseCode(), newAssignments.size(), students.size());
        for (User student : students) {
            if(student.getRole().toString() == "PROFESSOR" || student.getRole().toString() == "ADMIN"  )
            {
                logger.info("Skipping notification for user {} with role {}", student.getEmail(), student.getRole());
                 continue;
            }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(student.getEmail());
            message.setSubject("New Assignment(s) Added to " + course.getCourseName());
            
            StringBuilder messageText = new StringBuilder();
            messageText.append("Dear ").append(student.getFirstName()).append(",\n\n");
            messageText.append("New assignment(s) have been added to your course: ").append(course.getCourseCode());
            
            // Safely handle the case where courseInstructor might be null
            User instructor = course.getCourseInstructor();
            if (instructor != null) {
                messageText.append(" - Course Instructor: ").append(instructor.getFirstName());
            } else {
                messageText.append(" - Course Instructor: Not assigned");
            }
            messageText.append("\n\n");
            
            messageText.append("Assignment Details:\n");
            for (Assignment assignment : newAssignments) {
                messageText.append("- Title: ").append(assignment.getTitle()).append("\n");
                messageText.append("  Due Date: ").append(assignment.getDueDate()).append("\n");
                if (assignment.getDescription() != null && !assignment.getDescription().isEmpty()) {
                    messageText.append("  Description: ").append(assignment.getDescription()).append("\n");
                }
                messageText.append("\n");
            }
            
            messageText.append("Please log in to the university portal to view the full details and submit your work.\n\n");
            messageText.append("Regards,\nUniversity Team");
            
            message.setText(messageText.toString());
            javaMailSender.send(message);
        }
        return true;
    }

    @Async
    public boolean sendUsernameAndPasswordNotification(User user) {
        logger.info("Sending username and password notification to user: {}", user.getEmail());
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Your University Portal Account Information");
            
            StringBuilder messageText = new StringBuilder();
            messageText.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",\n\n");
            messageText.append("Welcome to the University Portal! Your account has been created successfully.\n\n");
            messageText.append("Your login credentials are as follows:\n");
            messageText.append("Username: ").append(user.getUsername()).append("\n");
            
            // Note: In a real application, you would never send an actual password in plain text
            // This is just for demonstration purposes. Typically, you would send a temporary 
            // password or a password reset link instead.
            messageText.append("Password: ").append(user.getPassword()).append("\n\n");
            
            messageText.append("Please log in to the university portal and change your password immediately for security reasons.\n\n");
            messageText.append("Regards,\nUniversity Team");
            
            message.setText(messageText.toString());
            javaMailSender.send(message);
            
            logger.info("Successfully sent account information to: {}", user.getEmail());
            return true;
        } catch (Exception e) {
            logger.error("Failed to send account information to: {}", user.getEmail(), e);
            return false;
        }
    }
}
