package com.joole.dao;

import org.springframework.data.repository.CrudRepository;
import com.joole.domain.User;

public interface UserDAO extends CrudRepository<User, Integer>{
	User findUserByUsername(String username);
	Boolean existsByUsername(String username);
}

