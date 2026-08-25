package com.diecast.diecast_back.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniaturaGaragemDTO {
	private Long idMiniatura;
	private Long idGaragem;
	private String nome;
	private GenericDTO marca;
	private List<GenericDTO> tipos;
	private GenericDTO condicao;
	private Long ano;
	private GenericDTO escala;
	private GenericDTO linha;
	private BigDecimal valor;
	private Long quantidadeEmGaragem;
    private Instant dataCadastro;
}
