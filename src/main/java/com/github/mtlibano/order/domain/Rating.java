package com.github.mtlibano.order.domain;

import com.github.mtlibano.order.domain.dto.RatingDTO;
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
@Entity(name = "rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	@Setter
	private Integer id;
	
	@Column(length = 1)
	private Integer grade;
	
	@Column(length = 150)
	private String comment;

	public Rating(RatingDTO dto) {
		this(dto.getId(), dto.getGrade(), dto.getComment());
	}

	public RatingDTO toDTO() {
		return new RatingDTO(id, grade, comment);
	}

}