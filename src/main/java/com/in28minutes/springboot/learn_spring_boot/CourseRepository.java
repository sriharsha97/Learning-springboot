package com.in28minutes.springboot.learn_spring_boot;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findById(long id);
    Course save(Course course);
    void deleteById(long id);
    Course update(long id, Course course);
    List<Course> searchByNameOrAuthor(String query);
    List<Course> findAllPagedSorted(int page, int size, String sortBy, boolean asc);
    List<Course> saveAll(List<Course> courses);
    void deleteAllByIds(List<Long> ids);
    long count();
    String mostCommonAuthor();
    Course patchUpdate(long id, String name, String authur);
}