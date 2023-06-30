package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
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

	private void validationPhoneNumber(PhoneNumber phoneNumber) {
		if (phoneNumber.getPhoneNumber() == null || phoneNumber.getPhoneNumber().equals("")) {
			throw new IntegrityViolation("Número telefone inválido!");
		}
		if (phoneNumber.getClient() == null) {
			throw new IntegrityViolation("Cliente inválido!");
		}
	}

	@Override
	public PhoneNumber insert(PhoneNumber phoneNumber) {
		validationPhoneNumber(phoneNumber);
		return repository.save(phoneNumber);
	}

	@Override
	public PhoneNumber update(PhoneNumber phoneNumber) {
		findById(phoneNumber.getId());
		validationPhoneNumber(phoneNumber);
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

	@Override
	public List<PhoneNumber> findByPhoneNumber(String phoneNumber) {
		List<PhoneNumber> list = repository.findByPhoneNumber(phoneNumber);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum telefone cadastrado com esse número: %s".formatted(phoneNumber));
		}
		return list;
	}

	@Override
	public List<PhoneNumber> findByClient(Client client) {
		List<PhoneNumber> list = repository.findByClient(client);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum telefone cadastrado para esse cliente: %s".formatted(client.getName()));
		}
		return list;
	}

}