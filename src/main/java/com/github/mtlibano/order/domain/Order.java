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
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false)
	private ZonedDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "id_payment")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "id_rating")
	private Rating rating;

}