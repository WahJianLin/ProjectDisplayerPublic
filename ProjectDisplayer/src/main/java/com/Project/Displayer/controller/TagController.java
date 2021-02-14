package com.Project.Displayer.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Project.Displayer.dao.ProjectDaoInterface;
import com.Project.Displayer.dao.TagDaoInterface;
import com.Project.Displayer.entity.Project;
import com.Project.Displayer.entity.Tag;
import com.Project.Displayer.input.TagInput;

@RestController
@RequestMapping(path = "/tag")
public class TagController implements ControllerAbstract<Tag, TagInput> {

	@Autowired
	private TagDaoInterface tagDao;

	@Autowired
	private ProjectDaoInterface projectDao;

	@Override
	public @ResponseBody String test() {
		return "test";
	}

	@Override
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Tag> getAll() {
		return tagDao.findAll();
	}

	@Override
	@GetMapping(path = "/get/{id}")
	public @ResponseBody Tag get(@PathVariable int id) throws Exception {
		Tag tag = tagDao.findById(id).get();

		if (tag.getTagId() == null) {
			throw new Exception("empty");
		}

		return tag;
	}

	@Override
	@PostMapping(path = "/add")
	public @ResponseBody Tag add(@RequestBody TagInput input) {
		Tag tag = new Tag();
		tag.setName(input.getName());
		tag.setDescription(input.getDescription());
		if (input.getProjects() != null && input.getProjects().length > 0) {
			Integer[] inputProjects = input.getProjects();
			Set<Project> projects = tag.getProjects();
			for (int i = 0; i < inputProjects.length; i++) {
				Project targetProject = projectDao.findById(inputProjects[i]).get();
				if (targetProject != null) {
					projects.addAll(Arrays.asList(targetProject));
					Set<Tag> targetProjectTags = targetProject.getTags();
					targetProjectTags.add(tag);
					targetProject.setTags(targetProjectTags);
				}
			}
			tag.setProjects(projects);
		}

		tagDao.save(tag);

		return tag;
	}

	@Override
	@PutMapping("/update/{id}")
	public @ResponseBody Tag update(@PathVariable int id, @RequestBody TagInput input) throws Exception {
		// TODO Auto-generated method stub
		Tag tagConstant = tagDao.findById(id).get();
		Tag tag = tagDao.findById(id).get();

		if (tag.getTagId() == null) {
			throw new Exception("empty");
		}

		if (!input.getName().isEmpty()) {
			tag.setName(input.getName());
		}
		if (!input.getDescription().isEmpty()) {
			tag.setDescription(input.getDescription());
		}

		Integer[] inputProjects = input.getProjects();
		Set<Project> projectsToRemove = new HashSet<Project>();
		projectsToRemove.addAll(tag.getProjects());

		Set<Tag> removeTags;
		Project projectToRemove;
		Project targetProject;
		
		if (input.getProjects() != null) {
			if (inputProjects.length > 0) {
				Set<Project> projects = tag.getProjects();
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
						Set<Tag> targetProjectTags = targetProject.getTags();
						targetProjectTags.add(tag);
						targetProject.setTags(targetProjectTags);
					}
				}
				for (Project removeProject : projectsToRemove) {
					projectToRemove = projectDao.findById(removeProject.getProjectId()).get();
					removeTags = projectToRemove.getTags();
					removeTags.remove(tagConstant);
				}
			} else {
				for (Project project : projectsToRemove) {
					projectToRemove = projectDao.findById(project.getProjectId()).get();
					removeTags = projectToRemove.getTags();
					removeTags.remove(tagConstant);
				}
			}
		}

		tagDao.save(tag);

		return tag;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception {
		// TODO Auto-generated method stub
		Tag tag = tagDao.findById(id).get();

		if (tag.getTagId() == null) {
			throw new Exception("empty");
		}

		tagDao.deleteById(id);

		return "Deleted";
	}

}
