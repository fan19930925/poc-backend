package com.joole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joole.domain.User;
import com.joole.jwt.JwtUser;
import com.joole.jwt.JwtUtils;
import com.joole.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	private static SecurityContext securityContext;

	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody JwtUser jwtUser) throws LockedException {

		String bearerToken = null;

		User user = userService.getUserByUsername((jwtUser.getUsername()));
		
		if (user != null) {

			if (user.getUsername().equals(jwtUser.getUsername())
					&& user.getPassword().equals(jwtUser.getPassword())) {

				bearerToken = jwtUtils.generateToken(user.getUsername());

				user.setSessionToken(bearerToken);

				return new ResponseEntity<User>(user, HttpStatus.OK);

			}

		}
		return ResponseEntity.notFound().build();

	}

	@RequestMapping(path = "/user/logout", method = RequestMethod.POST)
	public ResponseEntity<String> logout() {

		securityContext = SecurityContextHolder.getContext();
		System.out.println(securityContext.getAuthentication().getName() + "inside logout");
		securityContext.setAuthentication(null);

		if (securityContext.getAuthentication() == null) {

			return new ResponseEntity<String>("logout sucessful", HttpStatus.OK);
		}

		return new ResponseEntity<String>("user could not logout", HttpStatus.NO_CONTENT);

	}

	@RequestMapping(path = "/user/token/validity", method = RequestMethod.GET)
	public ResponseEntity<Boolean> tokenValidity() {

		securityContext = SecurityContextHolder.getContext();

		String user = securityContext.getAuthentication().getName();
		String token = securityContext.getAuthentication().getCredentials().toString();
		boolean validity = false;
		System.out.println(token+user);

		if ((!user.isEmpty() && user != null) && (token != null && !token.isEmpty())) {

			validity = jwtUtils.validateToken(token, user);

			if (validity) {

				return new ResponseEntity<Boolean>(validity, HttpStatus.OK);

			}

		}

		return new ResponseEntity<Boolean>(validity, HttpStatus.OK);
	}

	

}
