package com.github.mtlibano.order.domain;

import com.github.mtlibano.order.domain.dto.CityDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "city")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String description;
	
	@Column(nullable = false, length = 2)
	private String uf;

	public City(CityDTO dto) {
		this(dto.getId(), dto.getDescription(), dto.getUf());
	}

	public CityDTO toDTO() {
		return new CityDTO(id, description, uf);
	}
	
}