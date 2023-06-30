package com.github.mtlibano.order.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingClientDTO {
	
	private String name;
	
	private String cpf;
	
	private List<RatingDTO> ratings;

}