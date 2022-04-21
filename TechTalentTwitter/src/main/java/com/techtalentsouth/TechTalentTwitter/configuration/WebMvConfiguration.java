package com.techtalentsouth.TechTalentTwitter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvConfiguration implements WebMvcConfigurer{
	
	// Write the insrucitons to create a passwore\ encoder 
	// we put a method inside a class witht he configuration annotastion 
	// @Bean
	@Bean
	// the method must return the type your trying to create
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
}
