package com.github.mtlibano.order.domain;

import com.github.mtlibano.order.domain.dto.ProductOrderDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "product_order")
public class ProductOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 10)
	private Integer quantity;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private Order order;
	
	public ProductOrder(ProductOrderDTO dto, Product product, Order order) {
		this(dto.getId(), dto.getQuantity(), product, order);
	}
	
	public ProductOrderDTO toDTO() {
		return new ProductOrderDTO(id, quantity, product.getId(), product.getDescription(), order.getId());
	}

}