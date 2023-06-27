package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}