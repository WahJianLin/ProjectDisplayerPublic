package com.Project.Displayer.input;

import java.util.Arrays;

public class ProfileLinkInput {

	private Integer profileLinkId;
	private String name;
	private String linkType;
	private String link;
	private Integer[] projects;

	public Integer getProfileLinkId() {
		return profileLinkId;
	}

	public void setProfileLinkId(Integer profileLinkId) {
		this.profileLinkId = profileLinkId;
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

	public Integer[] getProjects() {
		return projects;
	}

	public void setProjects(Integer[] projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "ProfileLinkInput [profileLinkId=" + profileLinkId + ", name=" + name + ", linkType=" + linkType
				+ ", link=" + link + ", projects=" + Arrays.toString(projects) + "]";
	}

}
