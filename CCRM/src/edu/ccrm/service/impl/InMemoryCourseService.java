package edu.ccrm.service.impl;

import edu.ccrm.domain.Course;
import edu.ccrm.service.CourseService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class InMemoryCourseService implements CourseService {
    private final Map<String, Course> map = new ConcurrentHashMap<>();

    @Override
    public Course addCourse(Course c) {
        map.put(c.getCode(), c);
        return c;
    }

    @Override
    public Optional<Course> findByCode(String code) {
        return Optional.ofNullable(map.get(code));
    }

    @Override
    public List<Course> listAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void deactivate(String code) {
        Course c = map.get(code);
        if (c != null) c.deactivate();
    }

    @Override
    public Stream<Course> streamAll() {
        return map.values().stream();
    }
}