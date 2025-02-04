package com.SecourityDemo.SecurityProject.controller;

import com.SecourityDemo.SecurityProject.jwt.JwtUtils;
import com.SecourityDemo.SecurityProject.jwt.LoginRequest;
import com.SecourityDemo.SecurityProject.jwt.LoginResponse;
import com.SecourityDemo.SecurityProject.model.Students;
import com.SecourityDemo.SecurityProject.service.StudentService;
import com.SecourityDemo.SecurityProject.service.StudentServiceImple;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class StudentController {
    StudentService studentService = new StudentServiceImple();
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    private LoginRequest loginRequest;

    @GetMapping("/students")
    public List<Students> greet(){
        return studentService.getAllStudents();
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addstudents")
    public String addStudents(@RequestBody Students students){
        return studentService.addStudent(students);
    }


    @GetMapping("/authstatus")
    public String authStatus(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "User is not authenticated";
        }
        return "User is authenticated";
    }

    @GetMapping("/userdetails")
    public Map<String, Object> userdata(@AuthenticationPrincipal OAuth2User principal){
        return principal.getAttributes();
//        if(authentication != null && authentication.isAuthenticated()){
//            Object principal = authentication.getPrincipal();
//            if(principal instanceof OAuth2User oAuth2User){
//                return oAuth2User.getAttribute("email");
//            }
//        }
//        return "";
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request){
//        System.out.println(request.getHeader("Authorization"));
        logger.debug("Authorization Header: {}",request.getHeader("Authorization"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();



        Map<String,Object> profile = new HashMap<>();
        profile.put("username", userDetails.getUsername());
        profile.put("roles", userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList())
        );
        profile.put("message", "user specific");
        return ResponseEntity.ok(profile);
    }

}
