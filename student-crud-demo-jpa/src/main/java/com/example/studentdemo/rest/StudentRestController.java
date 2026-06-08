package com.example.studentdemo.rest;

import com.example.studentdemo.entity.Student;
import com.example.studentdemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(
            @RequestBody Student student) {

        student.setId(null);

        Student saved =
                studentService.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(
            @PathVariable Long id) {

        Student student =
                studentService.findById(id);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(
            @PathVariable Long id,
            @RequestBody Student student) {

        studentService.findById(id);

        student.setId(id);

        Student updated =
                studentService.save(student);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        studentService.findById(id);

        studentService.deleteById(id);

        return ResponseEntity.ok(
                "Deleted student with id: " + id);
    }
}