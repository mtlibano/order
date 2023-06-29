package com.github.mtlibano.order.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.domain.Product;

public interface ProductService {
	
	Product insert(Product product);

	Product update(Product product);

    void delete(Integer id);

    Product findById(Integer id);

	List<Product> listAll();

	List<Product> findByDescriptionIgnoreCase(String description);

	List<Product> findByPrice(BigDecimal price);

	List<Product> findByPriceBetween(BigDecimal initialPrice, BigDecimal finalPrice);
	
	Optional<Product> findByBarcode(String barcode);

}