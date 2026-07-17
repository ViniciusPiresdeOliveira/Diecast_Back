package com.diecast.diecast_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diecast.diecast_back.model.Garagem;

public interface GaragemRepository extends JpaRepository<Garagem, Long> {

	@Query("""
			SELECT g FROM Garagem g
			JOIN FETCH g.miniatura
			JOIN FETCH g.cliente
			WHERE g.cliente.id = :clienteId
			""")
	List<Garagem> findByClienteIdWithMiniatura(Long clienteId);
	
	boolean existsByClienteIdAndMiniaturaId(Long clienteId, Long miniaturaId);

}
