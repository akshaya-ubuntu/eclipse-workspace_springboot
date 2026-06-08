package com.example.studentdemo.service;

import com.example.studentdemo.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student save(Student student);

    Student findById(Long id);

    List<Student> findAll();

    void deleteById(Long id);
}
