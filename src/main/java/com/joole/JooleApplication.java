package com.joole;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.joole.dao.ProductDAO;
import com.joole.dao.UserDAO;
import com.joole.domain.Product;
import com.joole.domain.User;

@SpringBootApplication

public class JooleApplication{
	
	//private static final Logger log = LoggerFactory.getLogger(JooleApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(JooleApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(final UserDAO udao) {
//		return new CommandLineRunner(){
//			public void run(String... arg0) throws Exception {
//				((List<User>)udao.findAll()).forEach(e->System.out.println(e.getUsername()));;
//			}};
//	}
	
	@Bean
	public User user() {
		
		return new User();
		
	}
	@Bean
	public Product product() {
		return new Product();
	}
}
