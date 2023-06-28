package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.Payment;

public interface PaymentService {
	
	Payment insert(Payment payment);

	Payment update(Payment payment);

    void delete(Integer id);

    Payment findById(Integer id);

	List<Payment> listAll();

	List<Payment> findByTypeIgnoreCase(String type);

}