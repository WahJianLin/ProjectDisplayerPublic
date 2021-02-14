package com.Project.Displayer.controller;

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

import com.Project.Displayer.input.Input;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping
public interface ControllerAbstract<Entity, Input> {

	@GetMapping(path = "/test")
	public @ResponseBody String test();

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Entity> getAll();

	@PostMapping(path = "/get/{id}")
	public @ResponseBody Entity get(@PathVariable int id) throws Exception;

	@PostMapping(path = "/add")
	public @ResponseBody Entity add(@RequestBody Input input);

	@PutMapping(path = "/update/{id}")
	public @ResponseBody Entity update(@PathVariable int id, @RequestBody Input input) throws Exception;

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String delete(@PathVariable int id) throws Exception;
}
