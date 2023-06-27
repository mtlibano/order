package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.Order;

public interface OrderService {
	
	Order insert(Order order);
	Order update(Order order);
    void delete(Integer id);
    Order findById(Integer id);
	List<Order> listAll();

}