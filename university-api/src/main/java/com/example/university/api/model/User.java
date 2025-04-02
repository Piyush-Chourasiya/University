package com.example.university.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String Password;
    
    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    // Fields specific to professors
    @Column(name = "DEPARTMENT")
    private String department;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "STUDENT_COURSE_ENROLLMENTS",
        joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "COURSE_ID")
    )
    private Set<Course> enrolledCourses = new HashSet<>();
    
    // Relationships for professors
    @OneToMany(mappedBy = "courseInstructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> coursesTaught = new HashSet<>();
    
    // User submissions (for students)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssignmentSubmission> submissions = new HashSet<>();

    public enum UserRole {
        STUDENT,
        PROFESSOR,
        ADMIN
    }

}