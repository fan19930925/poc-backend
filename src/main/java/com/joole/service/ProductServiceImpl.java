package com.joole.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joole.dao.ProductDAO;
import com.joole.domain.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDAO;
	
	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) productDAO.findAll();
	}


	@Override
	public Boolean existProducts() {
		return productDAO.count() == 0 ? false : true;
	}
}
