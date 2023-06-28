package com.github.mtlibano.order.services;

import java.time.ZonedDateTime;
import java.util.List;

import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.domain.Payment;
import com.github.mtlibano.order.domain.Rating;

public interface OrderService {
	
	Order insert(Order order);

	Order update(Order order);

    void delete(Integer id);

    Order findById(Integer id);

	List<Order> listAll();

	List<Order> findByDate(ZonedDateTime date);

	List<Order> findByDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate);

	List<Order> findByClient(Client client);

	List<Order> findByPayment(Payment payment);

	List<Order> findByRating(Rating rating);

}