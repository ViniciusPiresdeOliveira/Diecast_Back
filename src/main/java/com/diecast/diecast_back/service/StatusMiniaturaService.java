package com.diecast.diecast_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.StatusMiniatura;
import com.diecast.diecast_back.repository.StatusMiniaturaRepository;

@Service

public class StatusMiniaturaService {
	private final StatusMiniaturaRepository repository;

	public StatusMiniaturaService(StatusMiniaturaRepository repository) {
		this.repository = repository;
	}

	public StatusMiniatura criar(StatusMiniatura status) {
		if (repository.existsByNome(status.getNome())) {
			throw new DatabaseException("O nome '" + status.getNome() + "' já está cadastrado.");
		}
		return repository.save(status);
	}

	public List<StatusMiniatura> listar() {
		return repository.findAll();
	}

	public StatusMiniatura buscarPorId(Short id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Não é possível encontrar: Status de miniatura com ID " + id + " não encontrado."));
	}

	public StatusMiniatura atualizar(Short id, StatusMiniatura novo) {
		StatusMiniatura existente = buscarPorId(id);
		existente.setNome(novo.getNome());
		return repository.save(existente);
	}

	public void deletar(Short id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Status de miniatura com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}
