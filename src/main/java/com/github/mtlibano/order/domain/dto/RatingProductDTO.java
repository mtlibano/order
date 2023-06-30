package com.github.mtlibano.order.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingProductDTO {
	
	private String description;
	
	private BigDecimal price;
	
	private List<RatingDTO> ratings;

}