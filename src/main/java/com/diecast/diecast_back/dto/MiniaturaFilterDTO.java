package com.diecast.diecast_back.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniaturaFilterDTO {
	private String nome;
	private List<Long> marcaIds;
	private Integer ano;
	private List<Long> tipoIds;
	private List<Long> linhaIds;
	private List<Long> statusIds;
	private List<Long> escalaIds;
	private BigDecimal precoMin;
	private BigDecimal precoMax;
	private Integer quantidadeDisponivelMin;
	private Integer quantidadeDisponivelMax;
	
	private int page = 0;
	private int size = 10;
}
