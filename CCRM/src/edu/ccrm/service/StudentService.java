package edu.ccrm.service;

import edu.ccrm.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student addStudent(Student s);
    Optional<Student> findById(String id);
    List<Student> listAll();
    Student update(Student s);
    void deactivate(String id);
}