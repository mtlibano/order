package com.github.mtlibano.order.repositories;

import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.domain.Payment;
import com.github.mtlibano.order.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Order;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByDate(ZonedDateTime date);

    List<Order> findByDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate);

    List<Order> findByClient(Client client);

    List<Order> findByPayment(Payment payment);

    List<Order> findByRating(Rating rating);

}