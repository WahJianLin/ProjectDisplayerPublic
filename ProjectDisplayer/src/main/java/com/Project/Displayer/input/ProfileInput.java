package com.Project.Displayer.input;

public class ProfileInput {

	private Integer profileId;
	private String name;
	private String story;
	private Integer contactInfoId;
	private Integer[] profileLinks;
	private Integer[] projects;

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public Integer getContactInfoId() {
		return contactInfoId;
	}

	public void setContactInfoId(Integer contactInfoId) {
		this.contactInfoId = contactInfoId;
	}

	public Integer[] getProfileLinks() {
		return profileLinks;
	}

	public void setProfileLinks(Integer[] profileLinks) {
		this.profileLinks = profileLinks;
	}

	public Integer[] getProjects() {
		return projects;
	}

	public void setProjects(Integer[] projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "ProfileInput [profileId=" + profileId + ", name=" + name + ", story=" + story + "]";
	}
}
