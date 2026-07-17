package com.diecast.diecast_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GaragemDTO {
    private Long miniaturaId;
    private Long clienteId;
    private Long quantidade;
}
