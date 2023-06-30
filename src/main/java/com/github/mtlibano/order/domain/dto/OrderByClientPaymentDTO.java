package com.github.mtlibano.order.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderByClientPaymentDTO {
	
	private String name;
	
	private String cpf;
	
	private String type;
	
	private List<OrderDTO> orders;

}