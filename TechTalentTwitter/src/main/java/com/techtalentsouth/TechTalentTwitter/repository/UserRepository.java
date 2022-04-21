package com.techtalentsouth.TechTalentTwitter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techtalentsouth.TechTalentTwitter.model.User;

// this is oging to be the interface by which we access the Database tables which hold users
// we do write the code for this instead we create an interface for what we want  andSB creates it based off our specifications
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	// the basic methods we add with crud are pretty much the bare mininmu to talk to the database table
	
	User findByUsername(String username);// syntax import findBy is acceptale spciefiescs we are gogin to find byUsername
	
}
