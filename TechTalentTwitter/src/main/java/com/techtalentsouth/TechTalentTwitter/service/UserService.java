package com.techtalentsouth.TechTalentTwitter.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techtalentsouth.TechTalentTwitter.model.Role;
import com.techtalentsouth.TechTalentTwitter.model.User;
import com.techtalentsouth.TechTalentTwitter.repository.RoleRepository;
import com.techtalentsouth.TechTalentTwitter.repository.UserRepository;

@Service// we can later asjk to have SB create a userservice  with a default constructor

public class UserService {
	// this is called injection it is asking Sb to automatically ccreat the class
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// Something is going to have to call this constructor  at some point to create this service
	// we wont be claling it insetead we WAnt SB to call This constructor when we as for a UserService
	@Autowired 
	public UserService(UserRepository userRepository,RoleRepository roleRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository= userRepository;
		this.roleRepository= roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User>findAll(){
		return (List<User>)userRepository.findAll();
	}
	public void save(User user) {
		userRepository.save(user);
	}
	
	public User saveNewUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		user.setActive(1);
		Role userRole=roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		return userRepository.save(user);// return Final copy of USER to database 
		
	}
	
	public User getLoggedInUser() {
		String loggedInUsername= SecurityContextHolder.getContext().getAuthentication().getName();
		
		return findByUsername(loggedInUsername);
	}
	
	
	
	
	
	
}
