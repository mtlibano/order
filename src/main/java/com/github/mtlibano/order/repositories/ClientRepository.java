package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Client;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer>{

    List<Client> findByNameIgnoreCase(String name);

    Optional<Client> findByCpf(String cpf);

    Optional<Client> findByEmail(String email);

}