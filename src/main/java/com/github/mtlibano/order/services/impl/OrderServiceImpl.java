package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.repositories.OrderRepository;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository repository;

	@Override
	public Order insert(Order order) {
		return repository.save(order);
	}

	@Override
	public Order update(Order order) {
		return repository.save(order);
	}

	@Override
	public void delete(Integer id) {
		Order order = findById(id);
        repository.delete(order);
	}

	@Override
	public Order findById(Integer id) {
		Optional<Order> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Order> listAll() {
		List<Order> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}