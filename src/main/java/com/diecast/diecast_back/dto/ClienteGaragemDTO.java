package com.diecast.diecast_back.dto;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteGaragemDTO {

    private Long id;
    private String nome;
    private List<MiniaturaGaragemDTO> miniaturas;
}
