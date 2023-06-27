package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.ProductOrder;
import com.github.mtlibano.order.repositories.ProductOrderRepository;
import com.github.mtlibano.order.services.ProductOrderService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
	
	@Autowired
	ProductOrderRepository repository;

	@Override
	public ProductOrder insert(ProductOrder productOrder) {
		return repository.save(productOrder);
	}

	@Override
	public ProductOrder update(ProductOrder productOrder) {
		return repository.save(productOrder);
	}

	@Override
	public void delete(Integer id) {
		ProductOrder productOrder = findById(id);
        repository.delete(productOrder);
	}

	@Override
	public ProductOrder findById(Integer id) {
		Optional<ProductOrder> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<ProductOrder> listAll() {
		List<ProductOrder> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}