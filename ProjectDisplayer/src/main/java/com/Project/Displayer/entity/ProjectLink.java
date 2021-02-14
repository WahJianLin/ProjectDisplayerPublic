package com.Project.Displayer.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ProjectLinks")
public class ProjectLink {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectLinkId;
	private String name;
	private String linkType;
	private String link;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectId")
	@JsonBackReference
	private Project project;

	public Integer getProjectLinkId() {
		return projectLinkId;
	}

	public void setProjectLinkId(Integer projectLinkId) {
		this.projectLinkId = projectLinkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "ProjectLink [projectLinkId=" + projectLinkId + ", name=" + name + ", linkType=" + linkType + ", link="
				+ link + ", project=" + project + "]";
	}

}
