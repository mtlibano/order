package com.github.mtlibano.order.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mtlibano.order.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	List<User> findByNameIgnoreCase(String name);
	
	List<User> findByEmailIgnoreCase(String email);
	
	Optional<User> findByEmail(String email);

}