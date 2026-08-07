package com.diecast.diecast_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diecast.diecast_back.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByTelefone(String telefone);

	@Query("""
			SELECT c FROM Cliente c
			WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
			   OR LOWER(c.telefone) LIKE LOWER(CONCAT('%', :termo, '%'))
			   OR LOWER(c.cep) LIKE LOWER(CONCAT('%', :termo, '%'))
			   OR LOWER(c.numeroResidencia) LIKE LOWER(CONCAT('%', :termo, '%'))
			   OR LOWER(CAST(c.id AS string)) LIKE LOWER(CONCAT('%', :termo, '%'))
			""")
	List<Cliente> search(@Param("termo") String termo);
}
