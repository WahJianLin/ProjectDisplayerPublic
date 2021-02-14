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

import com.Project.Displayer.dao.ContactInfoDaoInterface;
import com.Project.Displayer.entity.ContactInfo;
import com.Project.Displayer.input.ContactInfoInput;

@RestController
@RequestMapping(path = "/contactInfo")
public class ContactInfoController implements ControllerAbstract<ContactInfo, ContactInfoInput> {

	@Autowired
	private ContactInfoDaoInterface contactInfoDao;

	@Override
	@GetMapping(path = "/test")
	public @ResponseBody String test() {
		return "test";
	}

	@Override
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<ContactInfo> getAll() {
		return contactInfoDao.findAll();
	}

	@Override
	@GetMapping(path = "/get/{id}")
	public @ResponseBody ContactInfo get(@PathVariable int id) throws Exception {
		ContactInfo contactInfo = contactInfoDao.findById(id).get();

		if (contactInfo.getContactInfoId() == null) {
			throw new Exception("empty");
		}

		return contactInfo;
	}

	@Override
	@PostMapping(path = "/add")
	public @ResponseBody ContactInfo add(@RequestBody ContactInfoInput input) {
		ContactInfo contactInfo = new ContactInfo();

		contactInfo.setName(input.getName());
		contactInfo.setEmail(input.getEmail());
		
		contactInfoDao.save(contactInfo);

		return contactInfo;
	}

	@Override
	@PutMapping("/update/{id}")
	public @ResponseBody ContactInfo update(@PathVariable int id, @RequestBody ContactInfoInput input) throws Exception {
		// TODO Auto-generated method stub
		ContactInfo contactInfo = contactInfoDao.findById(id).get();

		if (contactInfo.getContactInfoId() == null) {
			throw new Exception("empty");
		}

		if (!input.getName().isEmpty()) {
			contactInfo.setName(input.getName());
		}
		if (!input.getEmail().isEmpty()) {
			contactInfo.setEmail(input.getEmail());
		}

		contactInfoDao.save(contactInfo);

		return contactInfo;
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception {
		// TODO Auto-generated method stub
		ContactInfo contactInfo = contactInfoDao.findById(id).get();

		if (contactInfo.getContactInfoId() == null) {
			throw new Exception("empty");
		}

		contactInfoDao.deleteById(id);

		return "Deleted";
	}

}
