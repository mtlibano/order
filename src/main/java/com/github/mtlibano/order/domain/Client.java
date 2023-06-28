package com.github.mtlibano.order.domain;

import java.time.ZonedDateTime;

import com.github.mtlibano.order.domain.dto.ClientDTO;
import com.github.mtlibano.order.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "client")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false, length = 150)
	private String name;
	
	@Column(nullable = false, length = 11, unique = true)
	private String cpf;
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(name = "birth_date")
	private ZonedDateTime birthDate;

	@Column(length = 8)
	private String cep;
	
	@Column(length = 150)
	private String street;

	@Column(length = 10)
	private String number;
	
	@Column(length = 150)
	private String district;
	
	@ManyToOne
	private City city;

	public Client(ClientDTO dto, City city) {
		this(dto.getId(), dto.getName(), dto.getCpf(), dto.getEmail(),
				DateUtils.strToZonedDateTime(dto.getBirthDate()),
				dto.getCep(), dto.getStreet(), dto.getNumber(), dto.getDistrict(),
				city);
	}

	public ClientDTO toDTO() {
		return new ClientDTO(id, name, cpf, email,
				DateUtils.zonedDateTimeToStr(birthDate),
				cep, street, number, district,
				city.getId(), city.getDescription(), city.getUf());
	}

}