package com.joole.dao;

import org.springframework.data.repository.CrudRepository;
import com.joole.domain.Product;

public interface ProductDAO extends CrudRepository<Product, Integer>{
	Product findProductById(int id);
}

