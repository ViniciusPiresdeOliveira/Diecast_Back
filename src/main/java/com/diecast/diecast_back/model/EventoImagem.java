package com.diecast.diecast_back.model;

import java.time.OffsetDateTime;
import java.util.Base64;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evento_imagens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EventoImagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@JsonIgnore
	private byte[] imagem;

	@CreationTimestamp
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "data_cadastro", updatable = false)
	private OffsetDateTime dataCadastro;

	@ManyToOne
	@JoinColumn(name = "evento_id", nullable = false)
	@JsonBackReference
	private Evento evento;
	
	@JsonProperty("imagem")
	public String getImagemBase64() {
	    if (this.imagem == null) return null;
	    return Base64.getEncoder().encodeToString(this.imagem);
	}
}
