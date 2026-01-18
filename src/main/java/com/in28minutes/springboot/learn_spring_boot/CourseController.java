package com.in28minutes.springboot.learn_spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream()
                .map(c -> new CourseDTO(c.getId(), c.getName(), c.getAuthur()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable long id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isPresent()) {
            Course c = courseOpt.get();
            return ResponseEntity.ok(new CourseDTO(c.getId(), c.getName(), c.getAuthur()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = new Course(0, courseDTO.getName(), courseDTO.getAuthur());
        Course saved = courseService.createCourse(course);
        return ResponseEntity.ok(new CourseDTO(saved.getId(), saved.getName(), saved.getAuthur()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable long id, @Valid @RequestBody CourseDTO courseDTO) {
        Course course = new Course(id, courseDTO.getName(), courseDTO.getAuthur());
        Course updated = courseService.updateCourse(id, course);
        if (updated != null) {
            return ResponseEntity.ok(new CourseDTO(updated.getId(), updated.getName(), updated.getAuthur()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // Search courses by name or author
    @GetMapping("/search")
    public List<CourseDTO> searchCourses(@RequestParam String query) {
        return courseService.searchCourses(query).stream()
            .map(c -> new CourseDTO(c.getId(), c.getName(), c.getAuthur()))
            .collect(Collectors.toList());
    }

    // Pagination and sorting
    @GetMapping("/paged")
    public List<CourseDTO> getCoursesPagedSorted(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "true") boolean asc) {
        return courseService.getCoursesPagedSorted(page, size, sortBy, asc).stream()
            .map(c -> new CourseDTO(c.getId(), c.getName(), c.getAuthur()))
            .collect(Collectors.toList());
    }

    // Bulk create
    @PostMapping("/bulk")
    public List<CourseDTO> createCoursesBulk(@Valid @RequestBody List<CourseDTO> courseDTOs) {
        List<Course> courses = courseDTOs.stream()
            .map(dto -> new Course(0, dto.getName(), dto.getAuthur()))
            .collect(Collectors.toList());
        return courseService.createCoursesBulk(courses).stream()
            .map(c -> new CourseDTO(c.getId(), c.getName(), c.getAuthur()))
            .collect(Collectors.toList());
    }

    // Bulk delete
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteCoursesBulk(@RequestBody List<Long> ids) {
        courseService.deleteCoursesBulk(ids);
        return ResponseEntity.noContent().build();
    }

    // Statistics: count
    @GetMapping("/count")
    public long getCourseCount() {
        return courseService.getCourseCount();
    }

    // Statistics: most common author
    @GetMapping("/most-common-author")
    public String getMostCommonAuthor() {
        return courseService.getMostCommonAuthor();
    }

    // PATCH: partial update
    @PatchMapping("/{id}")
    public ResponseEntity<CourseDTO> patchUpdateCourse(@PathVariable long id, @RequestBody CourseDTO courseDTO) {
        Course updated = courseService.patchUpdateCourse(
            id,
            courseDTO.getName(),
            courseDTO.getAuthur()
        );
        if (updated != null) {
            return ResponseEntity.ok(new CourseDTO(updated.getId(), updated.getName(), updated.getAuthur()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // CSV export
    @GetMapping("/export")
    public ResponseEntity<String> exportCoursesAsCsv() {
        List<Course> courses = courseService.getAllCourses();
        StringBuilder sb = new StringBuilder();
        sb.append("id,name,authur\n");
        for (Course c : courses) {
            sb.append(c.getId()).append(',')
              .append(c.getName().replace(",", " ")).append(',')
              .append(c.getAuthur().replace(",", " ")).append("\n");
        }
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=courses.csv")
            .header("Content-Type", "text/csv")
            .body(sb.toString());
    }
}