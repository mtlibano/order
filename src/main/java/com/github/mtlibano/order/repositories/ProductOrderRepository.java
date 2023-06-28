package com.github.mtlibano.order.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.domain.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
	
    List<ProductOrder> findByProduct(Product product);

    List<ProductOrder> findByOrder(Order order);

}