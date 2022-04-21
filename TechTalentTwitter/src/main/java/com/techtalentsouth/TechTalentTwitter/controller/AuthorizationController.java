package com.techtalentsouth.TechTalentTwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techtalentsouth.TechTalentTwitter.model.User;
import com.techtalentsouth.TechTalentTwitter.service.UserService;

// we are saying we are going to handle webrequest

@Controller
public class AuthorizationController {
	@Autowired
	private UserService userService;
	// specifiy what url(endpoint) do we want to present this page for 
	// this will preswetnt a login form 
	@GetMapping(value="/login")
	public String login() {
		return "login"; // value is a te3mplate that we are using
	}
	// this is goign to present a registrattoin form
	@GetMapping(value="/signup")
	public String registration(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		return "registration";
	}
	@PostMapping(value="/signup")
	public String createNewUser(@Valid User user,BindingResult bindingResult, Model model) {
		// user at this point holds th input form data
		// chechk to see if the user exisits
		User foundUser = userService.findByUsername(user.getUsername());
		if(foundUser !=null) {
			bindingResult.rejectValue("username", "error.user","Username is already taken");
		}
		if(!bindingResult.hasErrors()) {
			//process new user
			userService.saveNewUser(user);
			model.addAttribute("success","Sign up sucessful");
			model.addAttribute("user", new User());
		}
		return "registration";
	}
}
