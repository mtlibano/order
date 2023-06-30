package com.github.mtlibano.order.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientPaymentDTO {
	
    private Integer id;

    private String type;
    
    private List<ClientDTO> clients;

}