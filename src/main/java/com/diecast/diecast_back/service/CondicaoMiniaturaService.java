package com.diecast.diecast_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.CondicaoMiniatura;
import com.diecast.diecast_back.repository.CondicaoMiniaturaRepository;

@Service

public class CondicaoMiniaturaService {
	private final CondicaoMiniaturaRepository repository;

	public CondicaoMiniaturaService(CondicaoMiniaturaRepository repository) {
		this.repository = repository;
	}

	public CondicaoMiniatura criar(CondicaoMiniatura status) {
		if (repository.existsByNome(status.getNome())) {
			throw new DatabaseException("A condição '" + status.getNome() + "' já está cadastrada.");
		}
		return repository.save(status);
	}

	public List<CondicaoMiniatura> listar() {
		return repository.findAll();
	}

	public CondicaoMiniatura buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Não é possível encontrar: Condição de miniatura com ID " + id + " não encontrado."));
	}

	public CondicaoMiniatura atualizar(Long id, CondicaoMiniatura novo) {
		CondicaoMiniatura existente = buscarPorId(id);
		existente.setNome(novo.getNome());
		return repository.save(existente);
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Condição de miniatura com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}
