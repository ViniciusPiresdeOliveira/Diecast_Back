package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.diecast.diecast_back.model.LinkAfiliado;

public interface LinkAfiliadoRepository extends JpaRepository<LinkAfiliado, Long>, JpaSpecificationExecutor<LinkAfiliado>{
	
}

