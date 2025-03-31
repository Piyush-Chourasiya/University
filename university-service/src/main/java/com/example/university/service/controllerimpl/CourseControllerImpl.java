package com.example.university.service.controllerimpl;

import com.example.university.api.controller.CourseController;
import com.example.university.api.model.Course;
import com.example.university.api.model.User;
import com.example.university.service.services.CourseService;
import com.example.university.service.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseControllerImpl implements CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseControllerImpl.class);

    @Autowired
    private  CourseService courseService;
    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<Course>> getAllCourses(Boolean active) {
        if (active != null) {
            return ResponseEntity.ok(courseService.findAllByActive(active));
        }
        return ResponseEntity.ok(courseService.findAll());
    }

    @Override
    public ResponseEntity<Course> getCourseById(Integer id) {
        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Course> createCourse(Course course) {
        // Set creation timestamp
        course.setCreatedAt(LocalDateTime.now());
        
        // Ensure isActive is set
        if (course.getIsActive() == null) {
            course.setIsActive(true);
        }
        
        Course savedCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(Integer id) {
        return courseService.findById(id)
                .map(course -> {
                    courseService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Course> enrollStudent(Integer courseId, Integer studentId) {
        logger.info("Attempting to enroll student with ID {} to course with ID {}", studentId, courseId);
        
        Optional<Course> courseOpt = courseService.findById(courseId);
        logger.info("Course found: {}", courseOpt.isPresent());

        if (courseOpt.isPresent()) {
            logger.info("Course found: {}", courseOpt.get());
            Course course = courseOpt.get();
            logger.info("Course: {}", course);
            User student = userService.getUserById(studentId);
            logger.info("Student found: {}", student);
            if (student != null) {
                logger.info("Student found: {}", student);
                Course updatedCourse = courseService.enrollStudent(course, student);
                logger.info("Successfully enrolled student with ID {} to course with ID {}", studentId, courseId);
                return ResponseEntity.ok(updatedCourse);
            } else {
                logger.warn("Student with ID {} not found", studentId);
            }
        } else {
            logger.warn("Course with ID {} not found", courseId);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Course> unenrollStudent(Integer courseId, Integer studentId) {
        Optional<Course> courseOpt = courseService.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            User student = userService.getUserById(studentId);
            if (student != null) {
                Course updatedCourse = courseService.unenrollStudent(course, student);
                return ResponseEntity.ok(updatedCourse);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Course>> getCoursesByInstructor(Integer instructorId) {
        User instructor = userService.getUserById(instructorId);
        if (instructor != null) {
            List<Course> courses = courseService.findByInstructor(instructor);
            return ResponseEntity.ok(courses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<Course>> getCoursesBySemester(String semester) {
        return ResponseEntity.ok(courseService.findBySemester(semester));
    }

    @Override
    public ResponseEntity<Course> activateCourse(Integer id) {
        return courseService.findById(id)
                .map(course -> {
                    course.setIsActive(true);
                    Course updatedCourse = courseService.save(course);
                    return ResponseEntity.ok(updatedCourse);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Course> deactivateCourse(Integer id) {
        return courseService.findById(id)
                .map(course -> {
                    course.setIsActive(false);
                    Course updatedCourse = courseService.save(course);
                    return ResponseEntity.ok(updatedCourse);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
