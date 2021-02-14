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
@Table(name = "ProfileLinks")
public class ProfileLink {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer profileLinkId;
	private String name;
	private String linkType;
	private String link;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profileId")
	@JsonBackReference
	private Profile profile;

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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "ProfileLink [profileLinkId=" + profileLinkId + ", name=" + name + ", linkType=" + linkType + ", link="
				+ link + ", profile=" + profile + "]";
	}

}
