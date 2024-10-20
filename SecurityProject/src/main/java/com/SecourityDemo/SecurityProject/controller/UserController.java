package com.SecourityDemo.SecurityProject.controller;

import com.SecourityDemo.SecurityProject.jwt.*;
import com.SecourityDemo.SecurityProject.model.Students;
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
    public ResponseEntity<SignupResponse> createUser(@RequestBody SignUpRequest signUpRequestrequest){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        if (userDetailsManager.userExists(signUpRequestrequest.getUsername())){
            SignupResponse signupResponse =
                    new SignupResponse(signUpRequestrequest.getUsername(),
                    signUpRequestrequest.getPassword());
            return new ResponseEntity<>(signupResponse, HttpStatus.BAD_REQUEST);
        }

        UserDetails user = User.withUsername(signUpRequestrequest.getUsername())
                .password(passwordEncoder.encode(signUpRequestrequest.getPassword()))
                .roles(signUpRequestrequest.getRole())
                .build();

        userDetailsManager.createUser(user);

        SignupResponse signupResponse = new SignupResponse(signUpRequestrequest.getUsername(),signUpRequestrequest.getPassword());
        return new ResponseEntity<>(signupResponse, HttpStatus.OK);
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
