package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer>{

}