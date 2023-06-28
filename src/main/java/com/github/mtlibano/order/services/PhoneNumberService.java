package com.github.mtlibano.order.services;

import java.util.List;

import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.domain.PhoneNumber;

public interface PhoneNumberService {
	
	PhoneNumber insert(PhoneNumber phoneNumber);

	PhoneNumber update(PhoneNumber phoneNumber);

    void delete(Integer id);

    PhoneNumber findById(Integer id);

	List<PhoneNumber> listAll();

	List<PhoneNumber> findByPhoneNumber(String phoneNumber);

	List<PhoneNumber> findByClient(Client client);

}