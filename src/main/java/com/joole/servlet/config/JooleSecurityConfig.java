package com.joole.servlet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.joole.jwt.JwtAuthEntryPoint;
import com.joole.jwt.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JooleSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Autowired
	private UserDetailsService userDetService;

	@Autowired
	private JwtAuthEntryPoint jwtEntryPoint;

	@Autowired
	private JwtAuthFilter authFilter;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// configure AuthenticationManager so that it knows from where to load
	// user for matching credentials
	// Use BCryptPasswordEncoder .passwordEncoder(passwordEncoder()
		auth.userDetailsService(userDetService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().anyRequest().permitAll();

		http.csrf()//,"/user/token/validity"
			.disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		
		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors();
	}

}
