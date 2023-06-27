package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Payment;
import com.github.mtlibano.order.repositories.PaymentRepository;
import com.github.mtlibano.order.services.PaymentService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentRepository repository;

	@Override
	public Payment insert(Payment payment) {
		return repository.save(payment);
	}

	@Override
	public Payment update(Payment payment) {
		return repository.save(payment);
	}

	@Override
	public void delete(Integer id) {
		Payment payment = findById(id);
        repository.delete(payment);
	}

	@Override
	public Payment findById(Integer id) {
		Optional<Payment> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Payment> listAll() {
		List<Payment> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}