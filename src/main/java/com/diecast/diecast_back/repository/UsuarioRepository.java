package com.diecast.diecast_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.diecast.diecast_back.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	UserDetails findByLogin(String login);
}
