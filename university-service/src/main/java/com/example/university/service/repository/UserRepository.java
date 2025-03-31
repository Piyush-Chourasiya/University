package com.example.university.service.repository;

import com.example.university.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    
   
    Optional<User> findByEmail(String email);

    User findByUsername(String username);
    

    @Query("SELECT u FROM User u JOIN u.enrolledCourses c WHERE c.id = :courseId AND u.role = :role")
    List<User> findByCourseAndRole(@Param("courseId") Integer courseId, @Param("role") String role);
}