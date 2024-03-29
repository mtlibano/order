package com.github.mtlibano.order.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.domain.Client;

public interface ClientService {
	
	Client insert(Client client);

	Client update(Client client);

    void delete(Integer id);

    Client findById(Integer id);

	List<Client> listAll();

	List<Client> findByNameIgnoreCase(String name);
	
	List<Client> findByNameContainingIgnoreCase(String name);

	Optional<Client> findByCpf(String cpf);

    Optional<Client> findByEmailIgnoreCase(String email);
	
    List<Client> findByBirthDate(ZonedDateTime date);
    
    List<Client> findByBirthDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate);
    
    List<Client> findByStreetIgnoreCase(String street);
    
    List<Client> findByCep(String cep);
    
    List<Client> findByDistrictIgnoreCase(String district);
    
    List<Client> findByCity(City city);

}