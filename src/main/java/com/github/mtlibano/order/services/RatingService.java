package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.Rating;

public interface RatingService {
	
	Rating insert(Rating rating);
	Rating update(Rating rating);
    void delete(Integer id);
    Rating findById(Integer id);
	List<Rating> listAll();

}