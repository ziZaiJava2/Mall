package com.zzty.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zzty.entity.dtos.UserDTO;
import com.zzty.services.UserService;

@RestController
@RequestMapping(path="/users")
public class UserContoller {

	@Autowired
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public UserDTO getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
	
	@RequestMapping(path="", method=RequestMethod.POST)
	@ResponseStatus(code=HttpStatus.CREATED)
	public UserDTO createUser(@Valid @RequestBody UserDTO user) {
		// validate
		return userService.createUser(user);
	}
	
	@RequestMapping(path="", method=RequestMethod.GET)
	public List<UserDTO> getUsers(@RequestParam(required=false) Integer currentPage, @RequestParam(required=false) Integer pageSize) {
		return userService.getUsers(currentPage, pageSize);
	}
	
	
}
