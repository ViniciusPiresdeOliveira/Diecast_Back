package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diecast.diecast_back.model.MarcaMiniatura;

import java.util.Optional;

public interface MarcaMiniaturaRepository extends JpaRepository<MarcaMiniatura, Long> {

	Optional<MarcaMiniatura> findByNome(String nome);

	boolean existsByNome(String nome);
}
