package com.Project.Displayer.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Project.Displayer.dao.ProfileDaoInterface;
import com.Project.Displayer.dao.ProjectDaoInterface;
import com.Project.Displayer.dao.ProjectLinkDaoInterface;
import com.Project.Displayer.dao.TagDaoInterface;
import com.Project.Displayer.entity.Profile;
import com.Project.Displayer.entity.ProfileLink;
import com.Project.Displayer.entity.Project;
import com.Project.Displayer.entity.ProjectLink;
import com.Project.Displayer.entity.Tag;
import com.Project.Displayer.input.ProjectInput;

@RestController
@RequestMapping(path = "/project")
public class ProjectController implements ControllerAbstract<Project, ProjectInput> {

	@Autowired
	private ProjectDaoInterface projectDao;

	@Autowired
	private ProjectLinkDaoInterface projectLinkDao;

	@Autowired
	private ProfileDaoInterface profileDao;

	@Autowired
	private TagDaoInterface tagDao;

	@Override
	public @ResponseBody String test() {
		return "test";
	}

	@Override
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Project> getAll() {
		return projectDao.findAll();
	}

	@Override
	@GetMapping(path = "/get/{id}")
	public @ResponseBody Project get(@PathVariable int id) throws Exception {
		Project project = projectDao.findById(id).get();

		if (project.getProjectId() == null) {
			throw new Exception("empty");
		}

		return project;
	}

	@Override
	@PostMapping(path = "/add")
	public @ResponseBody Project add(@RequestBody ProjectInput input) {
		Project project = new Project();

		project.setName(input.getName());
		project.setDescription(input.getDescription());

		ProjectLink link;
		if (input.getProjectLinks() != null && input.getProjectLinks().length > 0) {
			Integer[] inputLinks = input.getProjectLinks();
			for (int i = 0; i < input.getProjectLinks().length; i++) {
				link = projectLinkDao.findById(inputLinks[i]).get();
				link.setProject(project);
			}
		}

		if (input.getProfiles() != null && input.getProfiles().length > 0) {
			Integer[] inputProfiles = input.getProfiles();
			Set<Profile> profiles = project.getProfiles();
			for (int i = 0; i < inputProfiles.length; i++) {
				Profile targetProfile = profileDao.findById(inputProfiles[i]).get();
				if (targetProfile != null) {
					profiles.addAll(Arrays.asList(targetProfile));
					Set<Project> targetProfileProjects = targetProfile.getProjects();
					targetProfileProjects.add(project);
					targetProfile.setProjects(targetProfileProjects);
				}
			}
			project.setProfiles(profiles);
		}

		if (input.getTags() != null && input.getTags().length > 0) {
			Integer[] inputTags = input.getTags();
			Set<Tag> tags = project.getTags();
			for (int i = 0; i < inputTags.length; i++) {
				Tag targetTag = tagDao.findById(inputTags[i]).get();
				if (targetTag != null) {
					tags.addAll(Arrays.asList(targetTag));
					Set<Project> targetTagProjects = targetTag.getProjects();
					targetTagProjects.add(project);
					targetTag.setProjects(targetTagProjects);
				}
			}
			project.setTags(tags);
		}

		projectDao.save(project);

		return project;
	}

	@Override
	@PutMapping("/update/{id}")
	public @ResponseBody Project update(@PathVariable int id, @RequestBody ProjectInput input) throws Exception {
		// TODO Auto-generated method stub
		Project project = projectDao.findById(id).get();

		ProjectLink link;

		if (project.getProjectId() == null) {
			throw new Exception("empty");
		}

		if (!input.getName().isEmpty()) {
			project.setName(input.getName());
		}
		if (!input.getDescription().isEmpty()) {
			project.setDescription(input.getDescription());
		}

		Set<ProjectLink> projectLinksToRemove = project.getProjectLinks();
		if (input.getProjectLinks() != null) {
			if (input.getProjectLinks().length > 0) {
				Integer[] inputLinks = input.getProjectLinks();
				for (int i = 0; i < input.getProjectLinks().length; i++) {
					link = projectLinkDao.findById(inputLinks[i]).get();
					if (projectLinksToRemove.contains(link)) {
						projectLinksToRemove.remove(link);
						continue;
					}
					link.setProject(project);
				}
				for (ProjectLink removeLink : projectLinksToRemove) {
					removeLink.setProject(null);
				}
			} else {
				for (ProjectLink removeLink : projectLinksToRemove) {
					removeLink.setProject(null);
				}
			}
		}

		if (input.getProfiles() != null) {
			Set<Profile> profiles = project.getProfiles();
			Set<Profile> profilesToRemove = new HashSet<Profile>();
			profilesToRemove.addAll(profiles);

			System.out.println("p1");
			if (input.getProfiles().length > 0) {

				System.out.println("p2");
				Integer[] inputProfiles = input.getProfiles();
				for (int i = 0; i < inputProfiles.length; i++) {
					Profile targetProfile = profileDao.findById(inputProfiles[i]).get();
					if (profilesToRemove.contains(targetProfile)) {
						profilesToRemove.remove(targetProfile);
						continue;
					}
					if (targetProfile != null) {
						profiles.addAll(Arrays.asList(targetProfile));
						Set<Project> targetProfileprojects = targetProfile.getProjects();
						targetProfileprojects.add(project);
						targetProfile.setProjects(targetProfileprojects);
					}
				}
				profiles.removeAll(profilesToRemove);
				project.setProfiles(profiles);
			} else {
				profiles.clear();
			}
		}

		if (input.getTags() != null) {
			System.out.println("t1");

			Set<Tag> tags = project.getTags();
			Set<Tag> tagsToRemove = new HashSet<Tag>();
			tagsToRemove.addAll(tags);
			if (input.getTags().length > 0) {
				System.out.println("t2");

				Integer[] inputTags = input.getTags();
				for (int i = 0; i < inputTags.length; i++) {
					Tag targetTag = tagDao.findById(inputTags[i]).get();
					if (tagsToRemove.contains(targetTag)) {
						tagsToRemove.remove(targetTag);
						continue;
					}
					if (targetTag != null) {
						tags.addAll(Arrays.asList(targetTag));
						Set<Project> targetTagProjects = targetTag.getProjects();
						targetTagProjects.add(project);
						targetTag.setProjects(targetTagProjects);
					}
				}
				tags.removeAll(tagsToRemove);
				project.setTags(tags);
			} else {
				tags.clear();
			}
		}
		System.out.println("f1");
		projectDao.save(project);
		System.out.println("f2");
		return project;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception {
		// TODO Auto-generated method stub
		Project project = projectDao.findById(id).get();

		if (project.getProjectId() == null) {
			throw new Exception("empty");
		}

		projectDao.deleteById(id);

		return "Deleted";
	}

}
