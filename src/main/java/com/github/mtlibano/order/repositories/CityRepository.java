package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.City;

public interface CityRepository extends JpaRepository<City, Integer> {

}