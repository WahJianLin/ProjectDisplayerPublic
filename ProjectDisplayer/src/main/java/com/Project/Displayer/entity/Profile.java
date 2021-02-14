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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Profiles")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer profileId;
	private String name;
	private String story;

	// done
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "contact_Info_Id", referencedColumnName = "contactInfoId")
	// @MapsId
	@JsonIgnoreProperties("profile")
	private ContactInfo contactInfo;

	// done
	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<ProfileLink> profileLinks = new HashSet<ProfileLink>();

	@ManyToMany(mappedBy = "profiles")
	@JsonIgnoreProperties("profiles")
	private Set<Project> projects = new HashSet<Project>();

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

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Set<ProfileLink> getProfileLinks() {
		return profileLinks;
	}

	public void setProfileLinks(Set<ProfileLink> profileLinks) {
		this.profileLinks = profileLinks;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Profile [profileId=" + profileId + ", name=" + name + ", story=" + story + ", contactInfo="
				+ contactInfo + ", profileLinks=" + profileLinks + ", projects=" + projects + "]";
	}

}
