package com.example.university.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


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
    
    @Column(name = "SUBMITTED_AT")
    private LocalDateTime submittedAt;
    
    @Column(name = "IS_LATE",columnDefinition = "DEFAULT false")
    private Boolean isLate = false;

    @Column(name = "SUBMISSION_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status submissionStatus;
    
    // Grade details
    // A ,B, C
    @Column(name = "GRADE")
    private String grade;
    
    @Column(name = "FEEDBACK", length = 1000)
    private String gradeFeedback;
    
    @ManyToOne
    @JoinColumn(name = "GRADER_ID")
    private User gradedBy;

    public enum Status{
        SUBMITTED,
        GRADED
    }
} 
