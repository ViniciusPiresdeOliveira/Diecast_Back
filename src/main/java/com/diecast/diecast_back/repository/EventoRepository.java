package com.diecast.diecast_back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diecast.diecast_back.model.Evento;
import com.diecast.diecast_back.model.Miniatura;

public interface EventoRepository extends JpaRepository<Evento, Long>, JpaSpecificationExecutor<Evento>{

	@Query("SELECT DISTINCT e FROM Evento e LEFT JOIN FETCH e.imagens")
	List<Evento> findAllWithImagens();
	
	@Query("SELECT e FROM Evento e LEFT JOIN FETCH e.imagens WHERE e.id = :id")
	Optional<Evento> findByIdWithImagens(@Param("id") Long id);
}
