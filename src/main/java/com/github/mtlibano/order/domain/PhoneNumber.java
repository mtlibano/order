package com.github.mtlibano.order.domain;

import com.github.mtlibano.order.domain.dto.PhoneNumberDTO;
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
@Entity(name = "phone_number")
public class PhoneNumber {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 12, name = "phone_number")
	private String phoneNumber;
	
	@ManyToOne
	private Client client;

	public PhoneNumber(PhoneNumberDTO dto, Client client) {
		this(dto.getId(), dto.getPhoneNumber(), client);
	}

	public PhoneNumberDTO toDTO() {
		return new PhoneNumberDTO(id, phoneNumber, client.getId(), client.getName());
	}

}