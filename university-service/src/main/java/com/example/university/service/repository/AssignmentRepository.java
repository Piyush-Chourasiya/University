package com.example.university.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.api.model.Assignment;
import com.example.university.api.model.Course;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByCourse(Course course);
}
