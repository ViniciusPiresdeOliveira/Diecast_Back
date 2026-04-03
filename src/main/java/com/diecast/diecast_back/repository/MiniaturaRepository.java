package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.diecast.diecast_back.model.Miniatura;

public interface MiniaturaRepository extends JpaRepository<Miniatura, Long>, JpaSpecificationExecutor<Miniatura>{
	
}
