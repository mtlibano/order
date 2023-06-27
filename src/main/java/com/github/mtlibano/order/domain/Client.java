package com.github.mtlibano.order.domain;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String name;
	
	@Column(nullable = false, length = 11, unique = true)
	private String cpf;
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(nullable = false, name = "birth_date")
	private ZonedDateTime birthDate;
	
	@Column(nullable = false, length = 150)
	private String street;
	
	@Column(nullable = false, length = 150)
	private String district;
	
	@Column(nullable = false, length = 8)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "id_city")
	private City city;

}