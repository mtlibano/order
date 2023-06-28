package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
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

	public void checkCity(City city) {
		if (city.getDescription() == null || city.getDescription() == "") {
			throw new IntegrityViolation("Descrição inválida!");
		}
		if (city.getUf() == null || city.getUf() == "") {
			throw new IntegrityViolation("UF inválido!");
		}
	}

	@Override
	public City insert(City city) {
		checkCity(city);
		return repository.save(city);
	}

	@Override
	public City update(City city) {
		findById(city.getId());
		checkCity(city);
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

	@Override
	public List<City> findByDescriptionIgnoreCase(String description) {
		List<City> list = repository.findByDescriptionIgnoreCase(description);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma cidade cadastrada com essa descrição: %s".formatted(description));
		}
		return list;
	}

	@Override
	public List<City> findByUfIgnoreCase(String uf) {
		List<City> list = repository.findByUfIgnoreCase(uf);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma cidade cadastrada com essa UF: %s".formatted(uf));
		}
		return list;
	}

}