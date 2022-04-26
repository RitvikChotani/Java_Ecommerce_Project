package com.shopme.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.service.UserService;

@RestController
public class UserRESTController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/users/check_email")
	public String checkEmailUnique(@Param("id") Integer id ,@Param("email") String email) {
		return userService.isEmailUnique(email,id) ? "OK" : "Duplicate";
	}
}
