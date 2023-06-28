package com.github.mtlibano.order.repositories;

import com.github.mtlibano.order.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mtlibano.order.domain.PhoneNumber;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {

    List<PhoneNumber> findByPhoneNumber(String phoneNumber);

    List<PhoneNumber> findByClient(Client client);

}