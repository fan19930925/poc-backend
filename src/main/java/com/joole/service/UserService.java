package com.joole.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.joole.domain.User;

public interface UserService {
	User getUserByUsername(String username);
	Boolean checkUserExistence(String username);
	UserDetails loadUserByUsername(String username);
}
