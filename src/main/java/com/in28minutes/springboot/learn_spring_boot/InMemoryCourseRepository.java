package com.in28minutes.springboot.learn_spring_boot;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCourseRepository implements CourseRepository {
    private final CopyOnWriteArrayList<Course> courses = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryCourseRepository() {
        courses.add(new Course(idGenerator.getAndIncrement(), "Learn spring boot", "in28minutes"));
        courses.add(new Course(idGenerator.getAndIncrement(), "Learn AWS", "in28minutes"));
    }

    @Override
    public List<Course> findAll() {
        return courses;
    }

    @Override
    public Optional<Course> findById(long id) {
        return courses.stream().filter(c -> c.getId() == id).findFirst();
    }

    @Override
    public Course save(Course course) {
        course = new Course(idGenerator.getAndIncrement(), course.getName(), course.getAuthur());
        courses.add(course);
        return course;
    }

    @Override
    public void deleteById(long id) {
        courses.removeIf(c -> c.getId() == id);
    }

    @Override
    public Course update(long id, Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == id) {
                Course course = new Course(id, updatedCourse.getName(), updatedCourse.getAuthur());
                courses.set(i, course);
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Course> searchByNameOrAuthor(String query) {
        String lowerQuery = query.toLowerCase();
        return courses.stream()
            .filter(c -> c.getName().toLowerCase().contains(lowerQuery) || c.getAuthur().toLowerCase().contains(lowerQuery))
            .toList();
    }

    @Override
    public List<Course> findAllPagedSorted(int page, int size, String sortBy, boolean asc) {
        return courses.stream()
            .sorted((c1, c2) -> {
                int cmp = 0;
                if ("name".equalsIgnoreCase(sortBy)) {
                    cmp = c1.getName().compareToIgnoreCase(c2.getName());
                } else if ("authur".equalsIgnoreCase(sortBy)) {
                    cmp = c1.getAuthur().compareToIgnoreCase(c2.getAuthur());
                } else {
                    cmp = Long.compare(c1.getId(), c2.getId());
                }
                return asc ? cmp : -cmp;
            })
            .skip((long) page * size)
            .limit(size)
            .toList();
    }

    @Override
    public List<Course> saveAll(List<Course> newCourses) {
        List<Course> saved = new java.util.ArrayList<>();
        for (Course nc : newCourses) {
            Course course = new Course(idGenerator.getAndIncrement(), nc.getName(), nc.getAuthur());
            courses.add(course);
            saved.add(course);
        }
        return saved;
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        courses.removeIf(c -> ids.contains(c.getId()));
    }

    @Override
    public long count() {
        return courses.size();
    }

    @Override
    public String mostCommonAuthor() {
        return courses.stream()
            .collect(java.util.stream.Collectors.groupingBy(Course::getAuthur, java.util.stream.Collectors.counting()))
            .entrySet().stream()
            .max(java.util.Map.Entry.comparingByValue())
            .map(java.util.Map.Entry::getKey)
            .orElse(null);
    }

    @Override
    public Course patchUpdate(long id, String name, String authur) {
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getId() == id) {
                String newName = name != null ? name : c.getName();
                String newAuthur = authur != null ? authur : c.getAuthur();
                Course updated = new Course(id, newName, newAuthur);
                courses.set(i, updated);
                return updated;
            }
        }
        return null;
    }
}