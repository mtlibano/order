package com.github.mtlibano.order.domain;

import java.time.ZonedDateTime;

import com.github.mtlibano.order.domain.dto.OrderDTO;
import com.github.mtlibano.order.utils.DateUtils;
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
@Entity(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(nullable = false)
	private ZonedDateTime date;
	
	@ManyToOne
	private Client client;
	
	@ManyToOne
	private Payment payment;
	
	@ManyToOne
	private Rating rating;

	public Order(OrderDTO dto, Client client, Payment payment, Rating rating) {
		this(dto.getId(),
				DateUtils.strToZonedDateTime(dto.getDate()),
				client,
				payment,
				rating);
	}

	public OrderDTO toDTO() {
		return new OrderDTO(id,
				DateUtils.zonedDateTimeToStr(date),
				client.getId(), client.getName(),
				payment.getId(), payment.getType(),
				rating.getId(), rating.getGrade(), rating.getComment());
	}

}