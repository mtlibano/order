package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.ProductOrder;

public interface ProductOrderService {
	
	ProductOrder insert(ProductOrder productOrder);
	ProductOrder update(ProductOrder productOrder);
    void delete(Integer id);
    ProductOrder findById(Integer id);
	List<ProductOrder> listAll();

}