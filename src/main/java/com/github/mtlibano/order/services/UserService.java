package com.github.mtlibano.order.services;

import java.util.List;

import com.github.mtlibano.order.domain.User;

public interface UserService {
	
	User save(User user);
	
	User update(User user);
	
	void delete (Integer id);
	
	User findById(Integer id);
	
	List<User> listAll();
	
	List<User> findByNameIgnoreCase(String name);
	
	List<User> findByEmailIgnoreCase(String email);

}