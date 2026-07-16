package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diecast.diecast_back.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByTelefone(String telefone);
}
