package com.diecast.diecast_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diecast.diecast_back.model.CondicaoMiniatura;

public interface CondicaoMiniaturaRepository extends JpaRepository<CondicaoMiniatura, Long> {

	Optional<CondicaoMiniatura> findByNome(String nome);

	boolean existsByNome(String nome);
}
