package com.github.mtlibano.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Integer id;
    private String name;
    private String cpf;
    private String email;
    private String date;
    private String cep;
    private String street;
    private String number;
    private String district;
    private Integer cityId;
    private String cityDescription;
    private String cityUf;

}