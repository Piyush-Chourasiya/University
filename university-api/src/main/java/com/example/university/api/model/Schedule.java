package com.example.university.api.model;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString; 

/**
 * Schedule class that represents course meeting times
 */
@Entity
@Table(name = "SCHEDULES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    
    @Column(name = "DAY_OF_WEEK", nullable = false)
    private String dayOfWeek;
    
    @Column(name = "START_TIME", nullable = false)
    private LocalTime startTime;
    
    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;
    
    @Column(name = "LOCATION")
    private String location;
    
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    
    @Column(name = "BUILDING")
    private String building;
    
}
