package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.repositories.CityRepository;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	CityRepository repository;

	@Override
	public City insert(City city) {
		return repository.save(city);
	}

	@Override
	public City update(City city) {
		return repository.save(city);
	}

	@Override
	public void delete(Integer id) {
		City city = findById(id);
        repository.delete(city);
	}

	@Override
	public City findById(Integer id) {
		Optional<City> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<City> listAll() {
		List<City> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}