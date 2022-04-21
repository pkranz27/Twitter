package com.techtalentsouth.TechTalentTwitter.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.techtalentsouth.TechTalentTwitter.model.User;
import com.techtalentsouth.TechTalentTwitter.service.UserService;

@Controller
public class FollowController {
	// procvces requeswt for a follow
	
	@Autowired
	private UserService userService;
	
	@PostMapping (value="/follow/{username}")
	public String follow(@PathVariable(value="username")String username,
							HttpServletRequest request) {
		
		User loggedInUser = userService.getLoggedInUser();
		User userToFollow = userService.findByUsername(username);
		List<User>followers = userToFollow.getFollowers();
		userToFollow.setFollowers(followers);
		followers.add(loggedInUser);
		userService.save(userToFollow);
		return"redirect:"+ request.getHeader("Referer");
	}
	@PostMapping (value="/unfollow/{username}")
	public String unfollow(@PathVariable(value="username")String username,
							HttpServletRequest request) {
		
		User loggedInUser = userService.getLoggedInUser();
		User userToUnfollow = userService.findByUsername(username);
		List<User>followers = userToUnfollow.getFollowers();
		userToUnfollow.setFollowers(followers);
		followers.add(loggedInUser);
		userService.save(userToUnfollow);
		return"redirect:"+ request.getHeader("Referer");
	}
}
