package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByGrade(Integer grade);

    List<Rating> findByGradeBetween(Integer initialGrade, Integer finalGrade);

}