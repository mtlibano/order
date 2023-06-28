package com.github.mtlibano.order.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

    private LocalDateTime time;

    private Integer status;

    private String erro;

    private String url;

}