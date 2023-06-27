package com.github.mtlibano.order.services;

import java.util.List;
import com.github.mtlibano.order.domain.Client;

public interface ClientService {
	
	Client insert(Client client);
	Client update(Client client);
    void delete(Integer id);
    Client findById(Integer id);
	List<Client> listAll();

}