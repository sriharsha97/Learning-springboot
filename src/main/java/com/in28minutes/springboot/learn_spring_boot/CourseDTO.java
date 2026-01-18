package com.in28minutes.springboot.learn_spring_boot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CourseDTO {
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Author is mandatory")
    @Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
    private String authur;

    public CourseDTO() {}

    public CourseDTO(long id, String name, String authur) {
        this.id = id;
        this.name = name;
        this.authur = authur;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthur() {
        return authur;
    }

    public void setAuthur(String authur) {
        this.authur = authur;
    }
}