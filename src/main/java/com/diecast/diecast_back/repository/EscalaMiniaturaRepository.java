package com.diecast.diecast_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diecast.diecast_back.model.EscalaMiniatura;

public interface EscalaMiniaturaRepository extends JpaRepository<EscalaMiniatura, Long> {

	Optional<EscalaMiniatura> findByNome(String nome);

	boolean existsByNome(String nome);
}
