package com.github.mtlibano.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDTO {

    private Integer id;

    private String phoneNumber;

    private Integer clientId;

    private String clientName;

}