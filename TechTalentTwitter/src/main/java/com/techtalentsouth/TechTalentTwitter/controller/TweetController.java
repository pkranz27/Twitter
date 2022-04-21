package com.techtalentsouth.TechTalentTwitter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techtalentsouth.TechTalentTwitter.model.Tag;
import com.techtalentsouth.TechTalentTwitter.model.Tweet;
import com.techtalentsouth.TechTalentTwitter.model.TweetDisplay;
import com.techtalentsouth.TechTalentTwitter.model.User;
import com.techtalentsouth.TechTalentTwitter.repository.TagRepository;
import com.techtalentsouth.TechTalentTwitter.service.TweetService;
import com.techtalentsouth.TechTalentTwitter.service.UserService;

@Controller
public class TweetController {
	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private TagRepository tagRepository;
	@GetMapping(value = { "/tweets", "/" })
    public String getFeed(@RequestParam(value = "filter", required = false) String filter, Model model) {
        User loggedInUser = userService.getLoggedInUser();
        List<TweetDisplay> tweets = new ArrayList<>();
        if (filter == null) {
            filter = "all";
        }
        if (filter.equalsIgnoreCase("following")) {
            List<User> following = loggedInUser.getFollowing();
            tweets = tweetService.findAllByUser(following);
            model.addAttribute("filter", "following");
        } else {
            tweets = tweetService.findAll();
            model.addAttribute("filter", "all");
        }
        model.addAttribute("tweetList", tweets);
        return "feed";
    }
	@GetMapping(value= {"/tweets/{tag}"})
	public String getTweetsByTag(@PathVariable(value ="tag") String tag, Model model) {
		List<TweetDisplay>tweets = tweetService.findAllWithTag(tag);
		model.addAttribute("tweetList", tweets);
		model.addAttribute("tag", tag);
		return "taggedTweets";
	}
	
	@GetMapping(value="/tweets/new")
	public String getTweetForm(Model model) {
		model.addAttribute("tweet", new Tweet());
		return "newTweet";		
	}
	
	@PostMapping(value="/tweets")
	public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
		User user = userService.getLoggedInUser();
		if(!bindingResult.hasErrors()) {
			//this code will only run if Tweet Passes Validation
			tweet.setUser(user);
			tweetService.save(tweet);
			model.addAttribute("successMessage","Tweet successfully created!");
			model.addAttribute("tweet", new Tweet());
			
			
		}
		return"newTweet";
	}
    @GetMapping(value = "/tags")
    public String getTags(Model model) {
    	List<Tag> tag = (List<Tag>)tagRepository.findAll();
    	model.addAttribute("tagList", tag);
    	return "tags";
    }
	
}
