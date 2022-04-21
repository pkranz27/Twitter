package com.techtalentsouth.TechTalentTwitter.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// we are going to store user in a database
// we use the annotation  from the JPAI
@Entity /// user creates a Database Table
@NoArgsConstructor
@AllArgsConstructor
@Data // genereates getters and setters a a to string methods
public class User {
	
	/// in order for Automatic sotring to user to a Database there has tot be a a default constructor
	// we can use a special tag to automatically doe this
	
	// label as primary key with ID annotation
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)    // Value which we generate the id by
	@Column(name="user_id")// contorls the name of the coulmn in the dtatabase 
	private Long id;
	
	@NotEmpty(message ="Please provide a Email")
	@Email(message="Please provide a valid Email")
	private String email;
	
	@NotEmpty(message = "Please provide a username")
	@Length(min=3, message="Your username must have 3 charcters")
	@Length(max=15, message="Your username must have 3 charcters")
	@Pattern(regexp ="[^\\s]+", message = "Username must not contain spaces")
	private String username;
	
	@NotEmpty(message = "Please provide a username")
	@Length(min=5, message="Your password must have 5 charcters")
	private String password;
	
	@NotEmpty(message = "Please provide your first name")
	private String firstName;
	@NotEmpty(message = "Please provide your last name")
	private String lastName;
	
	private int active;//1 means account is enabled
	
	@CreationTimestamp
	private Date createdAt;
	
	// This "User "
	// there is no good way to store a unlimted nuber of roles to a table entry
	// the way to store roles is by a many to many realtionship  and stored in a new table to user_Role id role_id
	// this etablishes a reltionship between the user and roles
	
	@ManyToMany(cascade = CascadeType.ALL)// automatically update the table when users or roles are deleted
	@JoinTable(name="user_role",joinColumns = @JoinColumn(name ="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_follower", joinColumns = @JoinColumn(name = "user_id"), 
	    inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private List<User> followers;
	
	@ManyToMany(mappedBy="followers")
	private List<User> following;
}
