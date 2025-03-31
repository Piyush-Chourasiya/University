package com.example.university.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * AssignmentSubmission class that represents student submissions
 * and their grades in a single entity
 */
@Entity
@Table(name = "ASSIGNMENT_SUBMISSIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignmentSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    private User student;
    
    @ManyToOne
    @JoinColumn(name = "ASSIGNMENT_ID", nullable = false)
    private Assignment assignment;
    
    // Submission details
    @Column(name = "SUBMISSION_CONTENT", length = 2000)
    private String submissionContent;
    
    @Column(name = "SUBMISSION_FILE_NAME")
    private String submissionFileName;
    
    @Column(name = "FILE_TYPE")
    private String fileType;
    
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    
    @Column(name = "SUBMITTED_AT")
    private LocalDateTime submittedAt;
    
    @Column(name = "IS_LATE")
    private Boolean isLate;
    
    @Column(name = "SUBMISSION_STATUS")
    private String submissionStatus; // "SUBMITTED", "RESUBMITTED", "UNDER_REVIEW", "GRADED"
    
    // Grade details
    
    @Column(name = "LETTER_GRADE")
    private String letterGrade;
    
    @Column(name = "FEEDBACK", length = 1000)
    private String feedback;
    
    @ManyToOne
    @JoinColumn(name = "GRADER_ID")
    private User gradedBy;
    
    @Column(name = "IS_GRADED")
    private Boolean isGraded = false;
} 
