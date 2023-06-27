package com.github.mtlibano.order.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String description;
	
	@Column(nullable = false, length = 10)
	private BigDecimal price;
	
	@Column(nullable = false, length = 13)
	private String barCode;
	
	

}
