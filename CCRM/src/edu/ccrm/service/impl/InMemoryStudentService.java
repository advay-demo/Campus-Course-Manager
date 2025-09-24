package edu.ccrm.service.impl;

import edu.ccrm.domain.Student;
import edu.ccrm.service.StudentService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStudentService implements StudentService {
    private final Map<String, Student> map = new ConcurrentHashMap<>();

    @Override
    public Student addStudent(Student s) {
        map.put(s.getId(), s);
        return s;
    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Student> listAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Student update(Student s) {
        map.put(s.getId(), s);
        return s;
    }

    @Override
    public void deactivate(String id) {
        Student s = map.get(id);
        if (s != null) s.deactivate();
    }
}