package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diecast.diecast_back.model.TipoMiniatura;

import java.util.Optional;

public interface TipoMiniaturaRepository extends JpaRepository<TipoMiniatura, Long> {

    Optional<TipoMiniatura> findByNome(String nome);

    boolean existsByNome(String nome);
}