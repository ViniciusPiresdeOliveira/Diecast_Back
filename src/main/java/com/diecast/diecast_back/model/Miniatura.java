package com.diecast.diecast_back.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "miniatura")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Miniatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "marca_id", nullable = false)
	private MarcaMiniatura marca;

	@ManyToMany
	@JoinTable(name = "miniatura_tipo", joinColumns = @JoinColumn(name = "miniatura_id"), inverseJoinColumns = @JoinColumn(name = "tipo_id"))
	private List<TipoMiniatura> tipos;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private StatusMiniatura status;

	@Lob
	@Column(name = "imagem", columnDefinition = "BYTEA")
	private byte[] imagem;

	private Long ano;

	@ManyToOne
	@JoinColumn(name = "escala_id")
	private EscalaMiniatura escala;

	@ManyToOne
	@JoinColumn(name = "linha_id")
	private LinhaMiniatura linha;
	
	@Column(name = "valor")
	private BigDecimal valor;
}
