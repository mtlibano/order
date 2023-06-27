package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}