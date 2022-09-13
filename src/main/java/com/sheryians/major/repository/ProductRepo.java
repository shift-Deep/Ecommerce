package com.sheryians.major.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
	
	List<Product> findAllByCategoryId(int id);

}
