package com.SecourityDemo.SecurityProject.service;

import com.SecourityDemo.SecurityProject.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImple implements StudentService {
    @Autowired
    private Students students;

    List<Students> lst = new ArrayList<>();

    @Override
    public List<Students> getAllStudents(){
        return lst;
    }

    @Override
    public String addStudent(Students students){
        lst.add(students);
        return "added";
    }

}
