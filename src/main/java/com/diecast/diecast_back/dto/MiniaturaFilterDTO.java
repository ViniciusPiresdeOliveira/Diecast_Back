package com.diecast.diecast_back.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniaturaFilterDTO {
	private String nome;
	private Long marcaId;
	private Integer ano;
	private Long tipoId;
	private Long linhaId;
	private String status;
	private String escala;
	private BigDecimal precoMin;
	private BigDecimal precoMax;

	private int page = 0;
	private int size = 10;
}
