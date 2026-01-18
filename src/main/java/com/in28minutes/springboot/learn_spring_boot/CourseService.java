package com.in28minutes.springboot.learn_spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(long id, Course course) {
        return courseRepository.update(id, course);
    }

    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> searchCourses(String query) {
        return courseRepository.searchByNameOrAuthor(query);
    }

    public List<Course> getCoursesPagedSorted(int page, int size, String sortBy, boolean asc) {
        return courseRepository.findAllPagedSorted(page, size, sortBy, asc);
    }

    public List<Course> createCoursesBulk(List<Course> courses) {
        return courseRepository.saveAll(courses);
    }

    public void deleteCoursesBulk(List<Long> ids) {
        courseRepository.deleteAllByIds(ids);
    }

    public long getCourseCount() {
        return courseRepository.count();
    }

    public String getMostCommonAuthor() {
        return courseRepository.mostCommonAuthor();
    }

    public Course patchUpdateCourse(long id, String name, String authur) {
        return courseRepository.patchUpdate(id, name, authur);
    }
}