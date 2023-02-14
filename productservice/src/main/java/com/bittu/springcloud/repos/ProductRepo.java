package com.bittu.springcloud.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bittu.springcloud.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
	
}
