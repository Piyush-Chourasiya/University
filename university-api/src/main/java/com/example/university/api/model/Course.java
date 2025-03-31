package com.example.university.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;


@Entity
@Table(name = "COURSES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "COURSE_NAME", nullable = false)
    private String courseName;
    
    @Column(name = "COURSE_CODE", nullable = false, unique = true)
    private String courseCode;
    
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @Column(name = "SEMESTER")
    private String semester;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTRUCTOR_ID")
    private User courseInstructor;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Schedule> schedules = new HashSet<>();
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Assignment> assignments = new HashSet<>();
    
    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<User> enrolledStudents = new HashSet<>();
    
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    
    @Column(name = "IS_ACTIVE")
    private Boolean isActive = false;
    
}