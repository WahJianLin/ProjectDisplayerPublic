package com.Project.Displayer.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Tags")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "tagId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tagId;
	private String name;
	private String description;
	/*
	 * @ManyToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinTable(name="projects_tags", joinColumns =
	 * {@JoinColumn(name="tag_id",referencedColumnName = "tagId")},
	 * inverseJoinColumns = {@JoinColumn(name="project_id",referencedColumnName =
	 * "projectId")})
	 */
	@ManyToMany(mappedBy = "tags")
	@JsonIgnoreProperties("tags")
	private Set<Project> projects = new HashSet<Project>();

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

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", name=" + name + ", description=" + description + ", projects=" + projects
				+ "]";
	}

}
