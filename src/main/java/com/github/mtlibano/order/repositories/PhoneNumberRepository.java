package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.PhoneNumber;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer>{

}