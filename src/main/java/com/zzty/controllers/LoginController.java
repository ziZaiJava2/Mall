package com.zzty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzty.entity.dtos.SessionInfoDTO;
import com.zzty.services.UserService;

@RestController
@RequestMapping(path="/login")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public SessionInfoDTO login(@RequestHeader("Authorization") String authValue){
		SessionInfoDTO sessionInfo = null;
		sessionInfo = userService.validateCreditial(authValue);
		return sessionInfo;
	}

}
