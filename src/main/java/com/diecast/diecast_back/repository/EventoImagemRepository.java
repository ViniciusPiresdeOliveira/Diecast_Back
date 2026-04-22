package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.diecast.diecast_back.model.EventoImagem;
import com.diecast.diecast_back.model.Miniatura;

public interface EventoImagemRepository extends JpaRepository<EventoImagem, Long>, JpaSpecificationExecutor<EventoImagem>{

}
