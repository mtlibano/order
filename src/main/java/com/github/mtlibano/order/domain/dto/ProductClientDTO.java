package com.github.mtlibano.order.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductClientDTO {
	
	private String name;
	
	private String cpf;
	
	private List<ProductDTO> products;

}
