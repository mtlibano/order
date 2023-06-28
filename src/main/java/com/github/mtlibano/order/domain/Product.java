package com.github.mtlibano.order.domain;

import java.math.BigDecimal;

import com.github.mtlibano.order.domain.dto.ProductDTO;
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
@Entity(name = "product")
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
	
	@Column(length = 13)
	private String barcode;

	public Product(ProductDTO dto) {
		this(dto.getId(), dto.getDescription(), dto.getPrice(), dto.getBarcode());
	}

	public ProductDTO toDTO() {
		return new ProductDTO(id, description, price, barcode);
	}

}