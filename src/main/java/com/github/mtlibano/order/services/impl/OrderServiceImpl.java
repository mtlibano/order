package com.github.mtlibano.order.services.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.domain.*;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.repositories.OrderRepository;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository repository;

	private void validationOrder(Order order) {
		if (order.getDate() == null) {
			throw new IntegrityViolation("Data inválida!");
		}
		if (order.getClient() == null) {
			throw new IntegrityViolation("Cliente inválido!");
		}
		if (order.getPayment() == null) {
			throw new IntegrityViolation("Pagamento inválido!");
		}
	}

	@Override
	public Order insert(Order order) {
		validationOrder(order);
		return repository.save(order);
	}

	@Override
	public Order update(Order order) {
		findById(order.getId());
		validationOrder(order);
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

	@Override
	public List<Order> findByDate(ZonedDateTime date) {
		List<Order> list = repository.findByDate(date);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum pedido com essa data: %s".formatted(date));
		}
		return list;
	}

	@Override
	public List<Order> findByDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate) {
		List<Order> list = repository.findByDateBetween(initialDate, finalDate);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum pedido nesse intervalo de data: %s e %s".formatted(initialDate, finalDate));
		}
		return list;
	}

	@Override
	public List<Order> findByClient(Client client) {
		List<Order> list = repository.findByClient(client);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum pedido para esse cliente: %s!".formatted(client.getName()));
		}
		return list;
	}

	@Override
	public List<Order> findByPayment(Payment payment) {
		List<Order> list = repository.findByPayment(payment);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum pedido para esse tipo de pagamento: %s!".formatted(payment.getType()));
		}
		return list;
	}

	@Override
	public List<Order> findByRating(Rating rating) {
		List<Order> list = repository.findByRating(rating);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum pedido com essa avaliação: %s!".formatted(rating.getId()));
		}
		return list;
	}

}