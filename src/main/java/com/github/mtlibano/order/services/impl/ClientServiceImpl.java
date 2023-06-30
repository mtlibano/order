package com.github.mtlibano.order.services.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.repositories.ClientRepository;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository repository;

	public void validationClient(Client client) {
		if (client.getName() == null || client.getName().equals("")) {
			throw new IntegrityViolation("Nome inválido!");
		}
		if (client.getCpf() == null || client.getCpf().equals("")) {
			throw new IntegrityViolation("CPF inválido!");
		}
		if (client.getEmail() == null || client.getEmail() == "") {
			throw new IntegrityViolation("Email inválido!");
		}
		duplicatedCpf(client);
		duplicatedEmail(client);
	}

	public void duplicatedCpf(Client client) {
		Optional<Client> testClient = repository.findByCpf(client.getCpf());
		if (testClient.isPresent()) {
			if (testClient.get().getId() != client.getId()) {
				throw new IntegrityViolation("CPF já utilizado!");
			}
		}
	}
	
	public void duplicatedEmail(Client client) {
		Optional<Client> testClient = repository.findByEmailIgnoreCase(client.getEmail());
		if (testClient.isPresent()) {
			if (testClient.get().getId() != client.getId()) {
				throw new IntegrityViolation("Email já utilizado!");
			}
		}
	}

	@Override
	public Client insert(Client client) {
		validationClient(client);
		return repository.save(client);
	}

	@Override
	public Client update(Client client) {
		validationClient(client);
		findById(client.getId());
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

	@Override
	public List<Client> findByNameIgnoreCase(String name) {
		List<Client> list = repository.findByNameIgnoreCase(name);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente cadastrado com esse nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public Optional<Client> findByCpf(String cpf) {
		Optional<Client> opt = repository.findByCpf(cpf);
		if (opt.isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente cadastrado com esse CPF: %s".formatted(cpf));
		}
		return opt;
	}

	@Override
	public Optional<Client> findByEmailIgnoreCase(String email) {
		Optional<Client> opt = repository.findByEmailIgnoreCase(email);
		if (opt.isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente cadastrado com esse email: %s".formatted(email));
		}
		return opt;
	}

	@Override
	public List<Client> findByBirthDate(ZonedDateTime date) {
		List<Client> list = repository.findByBirthDate(date);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente com essa data de nascimento: %s".formatted(date));
		}
		return list;
	}

	@Override
	public List<Client> findByBirthDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate) {
		List<Client> list = repository.findByBirthDateBetween(initialDate, finalDate);
		if (list.isEmpty()) {
			throw new ObjectNotFound(
					"Nenhum cliente nesse intervalo de data de nascimento: %s e %s".formatted(initialDate, finalDate));
		}
		return list;
	}

	@Override
	public List<Client> findByDistrictIgnoreCase(String district) {
		List<Client> list = repository.findByDistrictIgnoreCase(district);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente cadastrado com esse bairro: %s".formatted(district));
		}
		return list;
	}

	@Override
	public List<Client> findByCity(City city) {
		List<Client> list = repository.findByCity(city);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente cadastrado com essa cidade: %s".formatted(city.getDescription()));
		}
		return list;
	}

}