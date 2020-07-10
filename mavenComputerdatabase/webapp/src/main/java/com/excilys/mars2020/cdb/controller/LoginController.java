package com.excilys.mars2020.cdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.mars2020.cdb.dto.UserDTO;
import com.excilys.mars2020.cdb.service.UserService;

@Controller
public class LoginController {

	@SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userServ;
	
	@GetMapping("/loginAttempt")
	public ModelAndView credentialCheck (@RequestParam(name="username", required=true) String username,
										 @RequestParam(name="password", required=true) String password) {
		
		long resCredential = userServ.checkConnectionData(new UserDTO.Builder(username, password).build());
		if(resCredential < 0) {
			return new ModelAndView("login").addObject("error", true);
		}
		return new ModelAndView("dashboard");
	}
	
	@GetMapping("/login")
	public ModelAndView loginScreenLoad() {
		return new ModelAndView("login");
	}
}
