package com.diecast.diecast_back.dto;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniaturaDTO {
    private String nome;

    private Long marcaId;

    private List<Long> tiposIds;

    private Long statusId;

    private Long escalaId;

    private Short ano;

    private String imagem;
}
