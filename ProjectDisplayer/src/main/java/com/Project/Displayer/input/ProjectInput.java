package com.Project.Displayer.input;

public class ProjectInput {

	private Integer projectId;
	private String name;
	private String description;
	private Integer[] projectLinks;
	private Integer[] profiles;
	private Integer[] tags;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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

	public Integer[] getProjectLinks() {
		return projectLinks;
	}

	public void setProjectLinks(Integer[] projectLinks) {
		this.projectLinks = projectLinks;
	}

	public Integer[] getProfiles() {
		return profiles;
	}

	public void setProfiles(Integer[] profiles) {
		this.profiles = profiles;
	}

	public Integer[] getTags() {
		return tags;
	}

	public void setTags(Integer[] tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "ProjectInput [projectId=" + projectId + ", name=" + name + ", description=" + description + "]";
	}

}
