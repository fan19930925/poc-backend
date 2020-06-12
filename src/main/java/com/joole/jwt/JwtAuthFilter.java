package com.joole.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.joole.service.UserServiceImpl;
import com.joole.servlet.config.PrincipalUserManager;

import io.jsonwebtoken.SignatureException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	JwtUtils jwtUtility;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		String bearerToken = null;
		String userName = null;
		
		
		final String AuthRequestHeader = request.getHeader("Authorization");
		
		if (AuthRequestHeader != null && AuthRequestHeader.startsWith("Bearer ")) {
			bearerToken = AuthRequestHeader.substring(7);
			
			try {
				userName = jwtUtility.decodeJsonWebToken(bearerToken).getSubject();
				
			} catch (SignatureException sig) {
				sig.printStackTrace();
			}
		
			
		}
		
			
		SecurityContext securityContext = SecurityContextHolder.getContext();

		
		if (securityContext.getAuthentication() == null && userName != null ) {

			//reference to spring user DAO
			UserDetails userDetails = this.userService.loadUserByUsername(userName);
			
			
			if (jwtUtility.validateToken(bearerToken, userDetails.getUsername()) ) {
				
				UsernamePasswordAuthenticationToken userData = new UsernamePasswordAuthenticationToken(userDetails, bearerToken,null);
				userData.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				securityContext.setAuthentication(userData);
				
			}
		
		}
		
		filterChain.doFilter(request, response);
	}

}
