package com.Project.Displayer.input;

public class ProjectLinkInput {

	private Integer projectLinkId;
	private String name;
	private String linkType;
	private String link;
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
	@Override
	public String toString() {
		return "ProjectLinkInput [projectLinkId=" + projectLinkId + ", name=" + name + ", linkType=" + linkType
				+ ", link=" + link + "]";
	}
	
	
}
