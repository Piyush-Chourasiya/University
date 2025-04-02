package com.example.university.service.security;

import com.example.university.api.model.User;
import com.example.university.service.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@Order(1)
public class RoleCacheFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Thread-local variable to store the current user
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();
    
    // Public method to get the current user from anywhere in the application
    public static User getCurrentUser() {
        return currentUser.get();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Get the Authorization header
            String authHeader = request.getHeader("Authorization");
            
            // Check if the Authorization header exists and starts with "Basic "
            if (authHeader != null && authHeader.startsWith("Basic ")) {
                // Extract the credentials part after "Basic " and decode from Base64
                String base64Credentials = authHeader.substring("Basic ".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                
                // Split username and password
                final String[] values = credentials.split(":", 2);
                if (values.length < 2) {
                    throw new ServletException("Invalid Basic Authentication format");
                }
                
                String username = values[0];
                String password = values[1];
                
                if (username == null || username.isEmpty()) {
                    throw new ServletException("Username cannot be null or empty");
                }
                if (password == null) {
                    throw new ServletException("Password cannot be null");
                }
                
                // Find user by username
                User user = userRepository.findByUsername(username);
                if (user == null) {
                    throw new ServletException("User not found");
                }
                
                // Verify password
                if (passwordEncoder.matches(password, user.getPassword())) {
                    // Save user in the thread context globally
                    user.setPassword(" ");
                    currentUser.set(user);
                } else {
                    throw new ServletException("Incorrect password");
                }
            }
            
            // Continue the filter chain
            filterChain.doFilter(request, response);
        } finally {
            // Clear the ThreadLocal variable after the request is processed
            currentUser.remove();
        }
    }
}
