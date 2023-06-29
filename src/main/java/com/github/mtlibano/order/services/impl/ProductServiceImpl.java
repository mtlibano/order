package com.github.mtlibano.order.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.repositories.ProductRepository;
import com.github.mtlibano.order.services.ProductService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository repository;

	private void checkProduct(Product product) {
		if (product.getDescription() == null || product.getDescription().equals("")) {
			throw new IntegrityViolation("Descrição inválida!");
		}
		if (product.getPrice() == null) {
			throw new IntegrityViolation("Preço inválido!");
		}
	}

	@Override
	public Product insert(Product product) {
		checkProduct(product);
		return repository.save(product);
	}

	@Override
	public Product update(Product product) {
		findById(product.getId());
		checkProduct(product);
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

	@Override
	public List<Product> findByDescriptionIgnoreCase(String description) {
		List<Product> list = repository.findByDescriptionIgnoreCase(description);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum produto cadastrado com essa descrição: %s".formatted(description));
		}
		return list;
	}

	@Override
	public List<Product> findByPrice(BigDecimal price) {
		List<Product> list = repository.findByPrice(price);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum produto cadastrado com esse preço: %s".formatted(price));
		}
		return list;
	}

	@Override
	public List<Product> findByPriceBetween(BigDecimal initialPrice, BigDecimal finalPrice) {
		List<Product> list = repository.findByPriceBetween(initialPrice, finalPrice);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum produto nessa faixa de preço: %s e %s".formatted(initialPrice, finalPrice));
		}
		return list;
	}

	@Override
	public Optional<Product> findByBarcode(String barcode) {
		Optional<Product> opt = repository.findByBarcode(barcode);
		if (opt.isEmpty()) {
			throw new ObjectNotFound("Nenhum produto com esse código de barra: %s".formatted(barcode));
		}
		return opt;
	}

}