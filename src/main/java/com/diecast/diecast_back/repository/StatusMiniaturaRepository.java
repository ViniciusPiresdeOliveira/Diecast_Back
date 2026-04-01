package com.diecast.diecast_back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diecast.diecast_back.model.StatusMiniatura;

public interface StatusMiniaturaRepository extends JpaRepository<StatusMiniatura, Short> {

	Optional<StatusMiniatura> findByNome(String nome);

	boolean existsByNome(String nome);
}
