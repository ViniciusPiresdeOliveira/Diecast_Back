package com.diecast.diecast_back.dto;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniaturaDTO {
    private String nome;

    private Long marcaId;
    
    private List<Long> tiposIds;

    private Long condicaoId;

    private Long escalaId;
    
    private Long linhaId;

    private Long ano;
    
    private BigDecimal valor;

	private byte[] imagem;
	
    private String dataCadastro;
    
    private String dataAtualizacao;
    
    private Long quantidadeEstoque;

    private Long quantidadeDisponivel;

    private Long quantidadeEmGaragem;
}
