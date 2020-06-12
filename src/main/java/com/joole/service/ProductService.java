package com.joole.service;

import java.util.List;

import com.joole.domain.Product;

public interface ProductService {
	List<Product> getAllProducts();
	Boolean existProducts();
}
