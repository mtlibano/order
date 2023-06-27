package com.github.mtlibano.order.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.mtlibano.order.domain.Rating;
import com.github.mtlibano.order.repositories.RatingRepository;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@Service
public class RatingServiceImpl implements RatingService {
	
	@Autowired
	RatingRepository repository;

	@Override
	public Rating insert(Rating rating) {
		return repository.save(rating);
	}

	@Override
	public Rating update(Rating rating) {
		return repository.save(rating);
	}

	@Override
	public void delete(Integer id) {
		Rating rating = findById(id);
        repository.delete(rating);
	}

	@Override
	public Rating findById(Integer id) {
		Optional<Rating> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFound("ID %s não encontrado!".formatted(id)));
	}

	@Override
	public List<Rating> listAll() {
		List<Rating> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Void!");
        }
        return list;
	}

}