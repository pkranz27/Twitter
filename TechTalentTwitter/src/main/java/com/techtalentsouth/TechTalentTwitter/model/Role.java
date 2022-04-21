package com.techtalentsouth.TechTalentTwitter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="role_id")
	private Long id;
	private String role; // name or Role liek User Admin ETC
}
