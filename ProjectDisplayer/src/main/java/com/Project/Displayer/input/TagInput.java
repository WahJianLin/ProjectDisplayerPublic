package com.Project.Displayer.input;

public class TagInput {

	private Integer tagId;
	private String name;
	private String description;
	private Integer[] projects;
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer[] getProjects() {
		return projects;
	}
	public void setProjects(Integer[] projects) {
		this.projects = projects;
	}
	@Override
	public String toString() {
		return "TagInput [tagId=" + tagId + ", name=" + name + ", description=" + description + "]";
	}
	
}
