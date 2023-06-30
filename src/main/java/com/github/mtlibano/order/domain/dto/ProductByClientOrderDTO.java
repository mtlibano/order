package com.github.mtlibano.order.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductByClientOrderDTO {
	
	private String nome;
	
	private String cpf;
	
	private Integer id;
	
	private String date;
	
	private List<ProductDTO> products;

}