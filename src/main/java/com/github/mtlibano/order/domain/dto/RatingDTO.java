package com.github.mtlibano.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    private Integer id;

    private Integer grade;

    private String comment;

}