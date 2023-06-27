package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.repositories.ProductRepository;
import com.github.mtlibano.order.services.ProductService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository repository;

	@Override
	public Product insert(Product product) {
		return repository.save(product);
	}

	@Override
	public Product update(Product product) {
		return repository.save(product);
	}

	@Override
	public void delete(Integer id) {
		Product product = findById(id);
        repository.delete(product);
	}

	@Override
	public Product findById(Integer id) {
		Optional<Product> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Product> listAll() {
		List<Product> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}