package com.github.mtlibano.order.domain;

import com.github.mtlibano.order.domain.dto.PaymentDTO;
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
@Entity(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 40)
	private String type;

	public Payment(PaymentDTO dto) {
		this(dto.getId(), dto.getType());
	}

	public PaymentDTO toDTO() {
		return new PaymentDTO(id, type);
	}

}