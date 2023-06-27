package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

}