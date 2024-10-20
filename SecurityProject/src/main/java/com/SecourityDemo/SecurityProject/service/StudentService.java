package com.SecourityDemo.SecurityProject.service;

import com.SecourityDemo.SecurityProject.model.Students;

import java.util.List;

public interface StudentService {
    List<Students> getAllStudents();
    String addStudent(Students students);
}
