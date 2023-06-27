package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.PhoneNumber;
import com.github.mtlibano.order.repositories.PhoneNumberRepository;
import com.github.mtlibano.order.services.PhoneNumberService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {
	
	@Autowired
	PhoneNumberRepository repository;

	@Override
	public PhoneNumber insert(PhoneNumber phoneNumber) {
		return repository.save(phoneNumber);
	}

	@Override
	public PhoneNumber update(PhoneNumber phoneNumber) {
		return repository.save(phoneNumber);
	}

	@Override
	public void delete(Integer id) {
		PhoneNumber phoneNumber = findById(id);
        repository.delete(phoneNumber);
	}

	@Override
	public PhoneNumber findById(Integer id) {
		Optional<PhoneNumber> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<PhoneNumber> listAll() {
		List<PhoneNumber> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}