package com.joole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joole.dao.UserDAO;
import com.joole.domain.User;
import com.joole.servlet.config.PrincipalUserManager;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public User getUserByUsername(String username) {
		
		return userDAO.findUserByUsername(username);
	}
	
	@Override
	public Boolean checkUserExistence(String username) {
		
		return userDAO.existsByUsername(username);
	}
	

	/**method for DAOAuthenticationManager*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findUserByUsername(username);
		if (user== null) {
			return null;
		}
	
		return new PrincipalUserManager(user);
	}


}
