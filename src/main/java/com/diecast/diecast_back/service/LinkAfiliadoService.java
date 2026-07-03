package com.diecast.diecast_back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.LinkAfiliado;
import com.diecast.diecast_back.repository.LinkAfiliadoRepository;

@Service
public class LinkAfiliadoService {
	
	@Autowired 
	LinkAfiliadoRepository repository;

	public LinkAfiliadoService(LinkAfiliadoRepository repository) {
		this.repository = repository;
	}

	public LinkAfiliado criar(LinkAfiliado link) {
		return repository.save(link);
	}

	public List<LinkAfiliado> listar() {
		return repository.findAll();
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Link de Afiliado com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}
