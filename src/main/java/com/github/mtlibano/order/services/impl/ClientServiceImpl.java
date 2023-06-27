package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.repositories.ClientRepository;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	ClientRepository repository;

	@Override
	public Client insert(Client client) {
		return repository.save(client);
	}

	@Override
	public Client update(Client client) {
		return repository.save(client);
	}

	@Override
	public void delete(Integer id) {
		Client client = findById(id);
        repository.delete(client);
	}

	@Override
	public Client findById(Integer id) {
		Optional<Client> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Client> listAll() {
		List<Client> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}