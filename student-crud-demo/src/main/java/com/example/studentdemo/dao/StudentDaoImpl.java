package com.example.studentdemo.dao;

import com.example.studentdemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDaoImpl implements StudentDao {

    private final EntityManager entityManager;

    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Student save(Student student) {
        return entityManager.merge(student);
    }

    @Override
    public Optional<Student> findById(Long id) {

        Student student =
                entityManager.find(Student.class, id);

        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findAll() {

        TypedQuery<Student> query =
                entityManager.createQuery(
                        "FROM Student ORDER BY id",
                        Student.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Student student =
                entityManager.find(Student.class, id);

        if (student != null) {
            entityManager.remove(student);
        }
    }
}