package com.github.mtlibano.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mtlibano.order.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByDescriptionIgnoreCase(String description);

    List<Product> findByPrice(BigDecimal price);

    List<Product> findByPriceBetween(BigDecimal initialPrice, BigDecimal finalPrice);
    
    Optional<Product> findByBarcode(String barcode);

}