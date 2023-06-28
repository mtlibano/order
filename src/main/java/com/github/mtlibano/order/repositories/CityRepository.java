package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mtlibano.order.domain.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findByDescriptionIgnoreCase(String description);

    List<City> findByUfIgnoreCase(String uf);

}