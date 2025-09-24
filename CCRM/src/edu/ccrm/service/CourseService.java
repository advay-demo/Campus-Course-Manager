package edu.ccrm.service;

import edu.ccrm.domain.Course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CourseService {
    Course addCourse(Course c);
    Optional<Course> findByCode(String code);
    List<Course> listAll();
    void deactivate(String code);
    Stream<Course> streamAll();
}