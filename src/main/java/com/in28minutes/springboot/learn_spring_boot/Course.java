package com.in28minutes.springboot.learn_spring_boot;

public class Course {

	private long id;
	private String name;
	private String authur;
	
	public Course(long id, String name, String authur) {
		super();
		this.id = id;
		this.name = name;
		this.authur = authur;
	}


	public Course() {
		super();
	}


	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAuthur() {
		return authur;
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", authur=" + authur + "]";
	}

	
}