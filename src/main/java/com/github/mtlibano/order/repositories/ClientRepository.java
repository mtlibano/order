package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.mtlibano.order.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{

}