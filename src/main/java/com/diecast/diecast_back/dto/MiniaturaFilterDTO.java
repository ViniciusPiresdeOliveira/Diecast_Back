package com.diecast.diecast_back.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniaturaFilterDTO {
	  private String nome;
	    private Long marcaId;
	    private Integer ano;

	    private Integer page = 0;
	    private Integer size = 10;
}
