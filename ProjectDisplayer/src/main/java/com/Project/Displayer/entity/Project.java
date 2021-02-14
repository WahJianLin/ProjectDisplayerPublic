package com.Project.Displayer.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Projects")
//S@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "projectId")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;
	private String name;
	private String description;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<ProjectLink> projectLinks;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "projects_profiles", joinColumns = {
			@JoinColumn(name = "project_id", referencedColumnName = "projectId") }, inverseJoinColumns = {
					@JoinColumn(name = "profile_Id", referencedColumnName = "profileId") })
	// @ManyToMany(mappedBy = "projects")
	@JsonIgnoreProperties("projects")
	private Set<Profile> profiles = new HashSet<Profile>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "projects_tags", joinColumns = {
			@JoinColumn(name = "project_id", referencedColumnName = "projectId") }, inverseJoinColumns = {
					@JoinColumn(name = "tag_id", referencedColumnName = "tagId") })
	@JsonIgnoreProperties("projects")
	private Set<Tag> tags = new HashSet<Tag>();

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

	public Set<ProjectLink> getProjectLinks() {
		return projectLinks;
	}

	public void setProjectLinks(Set<ProjectLink> projectLinks) {
		this.projectLinks = projectLinks;
	}

	public Set<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", description=" + description + ", projectLinks="
				+ projectLinks + ", profiles=" + profiles + ", tags=" + tags + "]";
	}

}
