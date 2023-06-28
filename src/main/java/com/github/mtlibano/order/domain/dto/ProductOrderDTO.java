package com.github.mtlibano.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDTO {
	
	private Integer id;
	private Integer quantity;
	private Integer productId;
	private String productDescription;
	private Integer orderId;

}