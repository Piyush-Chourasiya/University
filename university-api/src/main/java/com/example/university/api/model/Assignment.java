package com.example.university.api.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "ASSIGNMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "TITLE", nullable = false)
    private String title;
    
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @Column(name = "DUE_DATE", nullable = false)
    private LocalDateTime dueDate;
    
    @ManyToOne
    @JoinColumn(name = "COURSE_ID", nullable = false)
    private Course course;
    
    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;
    
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    
    @Column(name = "UPDATED_AT")    
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssignmentSubmission> submissions = new HashSet<>();
}
