package com.joole.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joole.domain.Product;
import com.joole.service.ProductService;


@RequestMapping("/products")
@RestController
@CrossOrigin
public class ProductController {
	@Autowired
	ProductService prodService;

	@RequestMapping(value="/all_products",method=RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllProducts() {
		System.out.println(prodService.existProducts());
		if(prodService.existProducts()) {
			return new ResponseEntity<List<Product>>(prodService.getAllProducts(),HttpStatus.OK);
		}
		return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String getTest() {
		return "this is a test";
	}
}
