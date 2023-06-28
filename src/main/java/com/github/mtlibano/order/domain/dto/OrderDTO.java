package com.github.mtlibano.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer id;
    private String date;
    private Integer clientId;
    private String clientName;
    private Integer paymentId;
    private String paymentType;
    private Integer ratingId;
    private Integer ratingGrade;
    private String ratingComment;

}