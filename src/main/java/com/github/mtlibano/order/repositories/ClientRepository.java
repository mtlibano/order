package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.domain.Client;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

    List<Client> findByNameIgnoreCase(String name);

    Optional<Client> findByCpf(String cpf);

    Optional<Client> findByEmailIgnoreCase(String email);
    
    List<Client> findByBirthDate(ZonedDateTime date);
    
    List<Client> findByBirthDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate);
    
    List<Client> findByDistrictIgnoreCase(String district);
    
    List<Client> findByCity(City city);

}