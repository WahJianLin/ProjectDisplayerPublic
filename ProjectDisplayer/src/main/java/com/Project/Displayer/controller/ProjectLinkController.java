package com.Project.Displayer.controller;

import java.util.Optional;

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

import com.Project.Displayer.dao.ProjectLinkDaoInterface;
import com.Project.Displayer.entity.ProjectLink;
import com.Project.Displayer.input.ProjectLinkInput;

@RestController
@RequestMapping(path = "/projectLink")
public class ProjectLinkController implements ControllerAbstract<ProjectLink, ProjectLinkInput> {

	@Autowired
	private ProjectLinkDaoInterface projectLinkDao;

	@Override
	public @ResponseBody String test() {
		return "test";
	}

	@Override
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<ProjectLink> getAll() {
		return projectLinkDao.findAll();
	}

	@Override
	@GetMapping(path = "/get/{id}")
	public @ResponseBody ProjectLink get(@PathVariable int id) throws Exception {
		ProjectLink projectLink = projectLinkDao.findById(id).get();

		if (projectLink.getProjectLinkId() == null) {
			throw new Exception("empty");
		}

		return projectLink;
	}

	@Override
	@PostMapping(path = "/add")
	public @ResponseBody ProjectLink add(@RequestBody ProjectLinkInput input) {
		ProjectLink projectLink = new ProjectLink();

		projectLink.setName(input.getName());
		projectLink.setLink(input.getLink());
		projectLink.setLinkType(input.getLinkType());

		projectLinkDao.save(projectLink);

		return projectLink;
	}

	@Override
	@PutMapping("/update/{id}")
	public @ResponseBody ProjectLink update(@PathVariable int id, @RequestBody ProjectLinkInput input)
			throws Exception {
		// TODO Auto-generated method stub
		ProjectLink projectLink = projectLinkDao.findById(id).get();

		if (projectLink.getProjectLinkId() == null) {
			throw new Exception("empty");
		}
		if (input.getName() != null) {
			if (!input.getName().isEmpty()) {
				projectLink.setName(input.getName());
			}
		}
		if (input.getLink() != null) {
			if (!input.getLink().isEmpty()) {
				projectLink.setLink(input.getLink());
			}
		}
		if (input.getLinkType() != null) {
			if (!input.getLinkType().isEmpty()) {
				projectLink.setLinkType(input.getLinkType());
			}
		}

		projectLinkDao.save(projectLink);

		return projectLink;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception {
		// TODO Auto-generated method stub
		ProjectLink projectLink = projectLinkDao.findById(id).get();

		if (projectLink.getProjectLinkId() == null) {
			throw new Exception("empty");
		}

		projectLinkDao.deleteById(id);

		return "Deleted";
	}

}
