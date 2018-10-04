package com.nexiilabs.autolifecycle.usermanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserMgntController {
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/Json")
	public List<UserModel> getAll() {
		return service.getAll();
	}

}
