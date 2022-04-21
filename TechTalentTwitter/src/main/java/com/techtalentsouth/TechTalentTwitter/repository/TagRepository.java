package com.techtalentsouth.TechTalentTwitter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techtalentsouth.TechTalentTwitter.model.Tag;
@Repository
public interface TagRepository extends CrudRepository<Tag,Long>{
	Tag findByPhrase(String phrase);
	
}
