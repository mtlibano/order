package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}