package com.diecast.diecast_back.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diecast.diecast_back.model.LinhaMiniatura;

public interface LinhaMiniaturaRepository extends JpaRepository<LinhaMiniatura, Long> {

	Optional<LinhaMiniatura> findByNome(String nome);

	boolean existsByNome(String nome);
}
