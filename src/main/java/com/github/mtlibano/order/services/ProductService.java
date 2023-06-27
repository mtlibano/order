package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.Product;

public interface ProductService {
	
	Product insert(Product product);
	Product update(Product product);
    void delete(Integer id);
    Product findById(Integer id);
	List<Product> listAll();

}