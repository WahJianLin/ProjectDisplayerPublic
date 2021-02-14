package com.Project.Displayer.controller;

import java.util.Arrays;
import java.util.HashSet;
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

import com.Project.Displayer.dao.ContactInfoDaoInterface;
import com.Project.Displayer.dao.ProfileDaoInterface;
import com.Project.Displayer.dao.ProfileLinkDaoInterface;
import com.Project.Displayer.dao.ProjectDaoInterface;
import com.Project.Displayer.entity.ContactInfo;
import com.Project.Displayer.entity.Profile;
import com.Project.Displayer.entity.ProfileLink;
import com.Project.Displayer.entity.Project;
import com.Project.Displayer.entity.ProjectLink;
import com.Project.Displayer.entity.Tag;
import com.Project.Displayer.input.ProfileInput;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController implements ControllerAbstract<Profile, ProfileInput> {

	@Autowired
	private ProfileDaoInterface profileDao;

	@Autowired
	private ContactInfoDaoInterface contactDao;

	@Autowired
	private ProfileLinkDaoInterface profileLinkDao;

	@Autowired
	private ProjectDaoInterface projectDao;

	@Override
	public @ResponseBody String test() {
		return "test";
	}

	@Override
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Profile> getAll() {
		return profileDao.findAll();
	}

	@Override
	@GetMapping(path = "/get/{id}")
	public @ResponseBody Profile get(@PathVariable int id) throws Exception {
		Profile profile = profileDao.findById(id).get();

		if (profile.getProfileId() == null) {
			throw new Exception("empty");
		}

		return profile;
	}

	@Override
	@PostMapping(path = "/add")
	public @ResponseBody Profile add(@RequestBody ProfileInput input) {
		Profile profile = new Profile();

		profile.setName(input.getName());
		profile.setStory(input.getStory());

		if (input.getContactInfoId() != null && input.getContactInfoId() != null) {
			ContactInfo contactInfo = contactDao.findById(input.getContactInfoId()).get();
			profile.setContactInfo(contactInfo);
		}
		ProfileLink link;
		if (input.getProfileLinks() != null && input.getProfileLinks().length > 0) {
			Integer[] inputLinks = input.getProfileLinks();
			for (int i = 0; i < input.getProfileLinks().length; i++) {
				link = profileLinkDao.findById(inputLinks[i]).get();
				link.setProfile(profile);
			}
		}

		if (input.getProjects() != null && input.getProjects().length > 0) {
			Integer[] inputProjects = input.getProjects();
			Set<Project> projects = profile.getProjects();
			for (int i = 0; i < inputProjects.length; i++) {
				Project targetProject = projectDao.findById(inputProjects[i]).get();
				if (targetProject != null) {
					projects.addAll(Arrays.asList(targetProject));
					Set<Profile> targetProjectProfiles = targetProject.getProfiles();
					targetProjectProfiles.add(profile);
					targetProject.setProfiles(targetProjectProfiles);
				}
			}
			profile.setProjects(projects);
		}

		profileDao.save(profile);

		return profile;
	}

	@Override
	@PutMapping("/update/{id}")
	public @ResponseBody Profile update(@PathVariable int id, @RequestBody ProfileInput input) throws Exception {
		Profile profileConstant = profileDao.findById(id).get();
		Profile profile = profileDao.findById(id).get();
		if (profile.getProfileId() == null) {
			throw new Exception("empty");
		}

		ProfileLink link;

		if (input.getName() != null) {
			profile.setName(input.getName());
		}
		if (input.getStory() != null) {
			profile.setStory(input.getStory());
		}
		if (input.getContactInfoId() != null) {
			if (input.getContactInfoId() > 0) {
				ContactInfo contactInfo = contactDao.findById(input.getContactInfoId()).get();
				profile.setContactInfo(contactInfo);
			}
		}

		Set<ProfileLink> profileLinksToRemove = profile.getProfileLinks();
		if (input.getProfileLinks() != null) {
			if (input.getProfileLinks().length > 0) {
				Integer[] inputLinks = input.getProfileLinks();
				for (int i = 0; i < input.getProfileLinks().length; i++) {
					link = profileLinkDao.findById(inputLinks[i]).get();
					if (profileLinksToRemove.contains(link)) {
						profileLinksToRemove.remove(link);
						continue;
					}
					link.setProfile(profile);
				}
				for (ProfileLink removeLink : profileLinksToRemove) {
					removeLink.setProfile(profile);
				}
			} else {
				for (ProfileLink removeLink : profileLinksToRemove) {
					removeLink.setProfile(null);
				}
			}
		}

		Integer[] inputProjects = input.getProjects();
		Set<Project> projectsToRemove = new HashSet<Project>();
		projectsToRemove.addAll(profile.getProjects());

		Set<Profile> removeProfile;
		Project projectToRemove;
		Project targetProject;
		if (inputProjects != null) {
			if (inputProjects.length > 0) {
				Set<Project> projects = profile.getProjects();
				for (int i = 0; i < inputProjects.length; i++) {
					targetProject = projectDao.findById(inputProjects[i]).get();
					if (projectsToRemove.contains(targetProject)) {
						projectsToRemove.remove(targetProject);
						System.out.println("a:" + targetProject.getProjectId());
						continue;
					}

					if (targetProject != null) {
						System.out.println(targetProject.getProjectId());
						projects.addAll(Arrays.asList(targetProject));
						Set<Profile> targetProjectProfiles = targetProject.getProfiles();
						targetProjectProfiles.add(profile);
						targetProject.setProfiles(targetProjectProfiles);
					}
				}
				for (Project removeProject : projectsToRemove) {
					projectToRemove = projectDao.findById(removeProject.getProjectId()).get();
					removeProfile = projectToRemove.getProfiles();
					removeProfile.remove(profileConstant);
				}
			} else {
				System.out.println("pre: " + projectsToRemove.size());
				for (Project project : projectsToRemove) {
					projectToRemove = projectDao.findById(project.getProjectId()).get();
					removeProfile = projectToRemove.getProfiles();
					removeProfile.remove(profileConstant);
					System.out.println("Id " + project.getProjectId());
				}
				System.out.println("post: " + projectsToRemove.size());
			}
		}

		profileDao.save(profile);

		return profile;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception {
		// TODO Auto-generated method stub
		Profile profile = profileDao.findById(id).get();

		if (profile.getProfileId() == null) {
			throw new Exception("empty");
		}

		profileDao.deleteById(id);

		return "Deleted";
	}

}
