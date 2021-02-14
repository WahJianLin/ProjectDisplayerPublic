package com.Project.Displayer.controller;

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

import com.Project.Displayer.dao.ProfileLinkDaoInterface;
import com.Project.Displayer.entity.ProfileLink;
import com.Project.Displayer.input.ProfileLinkInput;

@RestController
@RequestMapping(path = "/profileLink")
public class ProfileLinkController implements ControllerAbstract<ProfileLink, ProfileLinkInput> {

	@Autowired
	private ProfileLinkDaoInterface profileLinkDao;

	@Override
	public @ResponseBody String test() {
		return "test";
	}

	@Override
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<ProfileLink> getAll() {
		return profileLinkDao.findAll();
	}

	@Override
	@GetMapping(path = "/get/{id}")
	public @ResponseBody ProfileLink get(@PathVariable int id) throws Exception {
		ProfileLink ProfileLink = profileLinkDao.findById(id).get();

		if (ProfileLink.getProfileLinkId() == null) {
			throw new Exception("empty");
		}

		return ProfileLink;
	}

	@Override
	@PostMapping(path = "/add")
	public @ResponseBody ProfileLink add(@RequestBody ProfileLinkInput input) {
		ProfileLink profileLink = new ProfileLink();

		profileLink.setName(input.getName());
		profileLink.setLink(input.getLink());
		profileLink.setLinkType(input.getLinkType());

		profileLinkDao.save(profileLink);

		return profileLink;
	}

	@Override
	@PutMapping("/update/{id}")
	public @ResponseBody ProfileLink update(@PathVariable int id, @RequestBody ProfileLinkInput input)
			throws Exception {
		// TODO Auto-generated method stub
		ProfileLink profileLink = profileLinkDao.findById(id).get();
		System.out.println(profileLink.getProfileLinkId());
		if (profileLink.getProfileLinkId() == null) {
			throw new Exception("empty");
		}
		if (input.getName() != null) {
			if (!input.getName().isEmpty()) {
				profileLink.setName(input.getName());
			}
		}
		if (input.getLink() != null) {
			if (!input.getLink().isEmpty()) {
				profileLink.setLink(input.getLink());
			}
		}
		if (input.getLinkType() != null) {
			if (!input.getLinkType().isEmpty()) {
				profileLink.setLinkType(input.getLinkType());
			}
		}

		profileLinkDao.save(profileLink);

		return profileLink;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception {
		// TODO Auto-generated method stub
		ProfileLink ProfileLink = profileLinkDao.findById(id).get();

		if (ProfileLink.getProfileLinkId() == null) {
			throw new Exception("empty");
		}

		profileLinkDao.deleteById(id);

		return "Deleted";
	}

}
