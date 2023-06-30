package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.User;
import com.github.mtlibano.order.repositories.UserRepository;
import com.github.mtlibano.order.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;
	
	public void validationEmail(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new IntegrityViolation("Email %s já foi utilizado!".formatted(user.getEmail()));
        }
	}

	@Override
	public User save(User user) {
		validationEmail(user);
		return repository.save(user);
	}

	@Override
	public User update(User user) {
		findById(user.getId());
		validationEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);		
	}

	@Override
	public User findById(Integer id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<User> listAll() {
		List<User> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Void!");
		}
		return list;
	}

	@Override
	public List<User> findByNameIgnoreCase(String name) {
		List<User> list = repository.findByNameIgnoreCase(name);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum usuário cadastrado com esse nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<User> findByEmailIgnoreCase(String email) {
		List<User> list = repository.findByEmailIgnoreCase(email);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum usuário cadastrado com esse email: %s".formatted(email));
		}
		return list;
	}

}