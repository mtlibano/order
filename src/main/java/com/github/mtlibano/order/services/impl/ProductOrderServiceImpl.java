package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.domain.ProductOrder;
import com.github.mtlibano.order.repositories.ProductOrderRepository;
import com.github.mtlibano.order.services.ProductOrderService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
	
	@Autowired
	ProductOrderRepository repository;
	
	private void validationProductOrder(ProductOrder productOrder) {
		if (productOrder.getQuantity() == null || productOrder.getQuantity() == 0) {
			throw new IntegrityViolation("Quantidade inválida!");
		}
		if (productOrder.getProduct() == null) {
			throw new IntegrityViolation("Produto inválido!");
		}
		if (productOrder.getOrder() == null) {
			throw new IntegrityViolation("Pedido inválido!");
		}
	}

	@Override
	public ProductOrder insert(ProductOrder productOrder) {
		validationProductOrder(productOrder);
		return repository.save(productOrder);
	}

	@Override
	public ProductOrder update(ProductOrder productOrder) {
		findById(productOrder.getId());
		validationProductOrder(productOrder);
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

	@Override
	public List<ProductOrder> findByProduct(Product product) {
		List<ProductOrder> list = repository.findByProduct(product);
        if (list.isEmpty()) {
        	throw new ObjectNotFound("Nenhum PedidoProduto com esse produto: %s!".formatted(product.getDescription()));
        }
        return list;
	}

	@Override
	public List<ProductOrder> findByOrder(Order order) {
		List<ProductOrder> list = repository.findByOrder(order);
        if (list.isEmpty()) {
        	throw new ObjectNotFound("Nenhum PedidoProduto com esse pedido: %s!".formatted(order.getId()));
        }
        return list;
	}

}