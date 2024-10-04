package com.SecourityDemo.SecurityProject.controller;

import com.SecourityDemo.SecurityProject.jwt.JwtUtils;
import com.SecourityDemo.SecurityProject.jwt.LoginRequest;
import com.SecourityDemo.SecurityProject.jwt.LoginResponse;
import com.SecourityDemo.SecurityProject.jwt.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private LoginRequest loginRequest;



    @PostMapping("/signup")
    public String createUser(@RequestBody SignUpRequest signUpRequestrequest){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        System.out.println(signUpRequestrequest.getUsername());
        if (userDetailsManager.userExists(signUpRequestrequest.getUsername())){
            return "User already exists";
        }

        UserDetails user = User.withUsername(signUpRequestrequest.getUsername())
                .password(passwordEncoder.encode(signUpRequestrequest.getPassword()))
                .roles(signUpRequestrequest.getRole())
                .build();

        userDetailsManager.createUser(user);
        return "User created";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(response);
    }


}
