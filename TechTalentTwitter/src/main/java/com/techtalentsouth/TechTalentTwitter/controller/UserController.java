package com.techtalentsouth.TechTalentTwitter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.techtalentsouth.TechTalentTwitter.model.Tweet;
import com.techtalentsouth.TechTalentTwitter.model.TweetDisplay;
import com.techtalentsouth.TechTalentTwitter.model.User;
import com.techtalentsouth.TechTalentTwitter.service.TweetService;
import com.techtalentsouth.TechTalentTwitter.service.UserService;

@Controller
public class UserController {
	    
	    @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private TweetService tweetService;
	    
	    
	    @GetMapping(value = "/users/{username}")
	public String getUser(@PathVariable(value="username") String username, Model model) {	
	    	// we are going to collect ingotmation about user ifi it is a valid user
	    	// and present the that information to template by passing it through the model varaibele
	    User loggedInUser = userService.getLoggedInUser();
	    List<User> following = loggedInUser.getFollowing();
	    boolean isFollowing = false;
	    for (User followedUser : following) {
	    	if (followedUser.getUsername().equals(username)) {
	    			isFollowing = true;
	    	}
	   }
	    boolean isSelfPage = loggedInUser.getUsername().equals(username);
	    model.addAttribute("following", isFollowing);
	    User user = userService.findByUsername(username);
	    List<TweetDisplay> tweets = tweetService.findAllByUser(user);
	    model.addAttribute("tweetList", tweets);
	    model.addAttribute("isSelfPage", isSelfPage);
	    model.addAttribute("user", user);
	    return "user";
	}
	    @GetMapping(value="/users")
	    public String getUsers(Model model) {
	    	List<User> users = userService.findAll();
	    	User loggedInUser = userService.getLoggedInUser();
	    	List<User> usersFollowing = loggedInUser.getFollowing();
	    	SetFollowingStatus(users, usersFollowing, model);
	    	model.addAttribute("users", users);
	    	setTweetCounts(users,model);
	    	return "users";
	    	
	    }
	    
	    private void setTweetCounts(List<User> users, Model model) {
	    	Map<String,Integer> tweetCounts = new HashMap<>();
	    	for(User user: users) {
	    		List<TweetDisplay> tweets = tweetService.findAllByUser(user);
	    		tweetCounts.put(user.getUsername(), tweets.size());
	    	}
	    	model.addAttribute("tweetCounts", tweetCounts);
	    }
	    
	    private void SetFollowingStatus(List<User> users, List<User> usersFollowing, Model model) {
			HashMap<String, Boolean> followingStatus = new HashMap<>();
			String username = userService.getLoggedInUser().getUsername();
			for (User user : users) {
				if (usersFollowing.contains(user)) {
					followingStatus.put(user.getUsername(), true);
				} else if (!user.getUsername().equals(username)) {
					followingStatus.put(user.getUsername(), false);
				}
			}
			model.addAttribute("followingStatus", followingStatus);
		}
	    
	}

