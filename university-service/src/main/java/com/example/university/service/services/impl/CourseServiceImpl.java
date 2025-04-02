package com.example.university.service.services.impl;

import com.example.university.api.model.Course;
import com.example.university.api.model.User;

import com.example.university.service.repository.CourseRepository;
import com.example.university.service.services.CourseService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
    
    @Override
    public List<Course> findAllByActive(Boolean active) {
        return courseRepository.findByIsActive(active);
    }
    
    @Override
    public Optional<Course> findById(Integer id) {
        return courseRepository.findById(id);
    }
    
    @Override
    @Transactional
    public Course save(Course course) {
        logger.debug("Saving course: {}", course);
        
        if (course == null) {
            logger.error("Failed to save course: course object is null");
            return null;
        }
    
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            logger.error("Failed to save course: course name is empty");
        }
        
        User courseInstructor = course.getCourseInstructor();
        
        Course savedCourse = courseRepository.save(course);
        logger.info("Course saved successfully with ID: {}", savedCourse.getId());

            logger.debug("Setting instructor ID: {} for course ID: {}", 
                    courseInstructor.getId(), savedCourse.getId());
            savedCourse.setCourseInstructor(courseInstructor);
        return savedCourse;
    }
   
    @Override

    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }
    
    @Override
    public Course enrollStudent(Course course, User student) {
        logger.debug("Enrolling student ID: {} to course ID: {}", student.getId(), course.getId());
        
        // Add student to the course's enrolled students
        course.getEnrolledStudents().add(student);
        logger.info("Student ID: {} added to course ID: {}", student.getId(), course.getId());
        
        // Add course to the student's enrolled courses
        student.getEnrolledCourses().add(course);
        logger.info("Course ID: {} added to student ID: {}'s enrolled courses", course.getId(), student.getId());
        
        // Save and return the updated course
        Course updatedCourse = courseRepository.save(course);
        logger.info("Course ID: {} updated successfully after enrolling student ID: {}", updatedCourse.getId(), student.getId());
        
        return updatedCourse;
    }
    
    @Override
    public Course unenrollStudent(Course course, User student) {
        // Remove student from the course's enrolled students
        course.getEnrolledStudents().remove(student);
        
        // Remove course from the student's enrolled courses
        student.getEnrolledCourses().remove(course);
        
        // Save and return the updated course
        return courseRepository.save(course);
    }
    
    @Override
    public List<Course> findByInstructor(User instructor) {
        return courseRepository.findByCourseInstructor(instructor);
    }
    
    @Override
    public List<Course> findBySemester(String semester) {
        return courseRepository.findBySemester(semester);
    }
}
