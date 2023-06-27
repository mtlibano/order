package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.City;

public interface CityService {
	
	City insert(City city);
	City update(City city);
    void delete(Integer id);
	City findById(Integer id);
	List<City> listAll();

}