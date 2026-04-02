package com.diecast.diecast_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.EscalaMiniatura;
import com.diecast.diecast_back.repository.EscalaMiniaturaRepository;

@Service
public class EscalaMiniaturaService {
	private final EscalaMiniaturaRepository repository;

	public EscalaMiniaturaService(EscalaMiniaturaRepository repository) {
		this.repository = repository;
	}

	public EscalaMiniatura criar(EscalaMiniatura escala) {
		if (repository.existsByNome(escala.getNome())) {
			throw new DatabaseException("O nome '" + escala.getNome() + "' já está cadastrado.");
		}
		return repository.save(escala);
	}

	public List<EscalaMiniatura> listar() {
		return repository.findAll();
	}

	public EscalaMiniatura buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Não é possível encontrar: Escala de miniatura com ID " + id + " não encontrada."));
	}

	public EscalaMiniatura atualizar(Long id, EscalaMiniatura novo) {
		EscalaMiniatura existente = buscarPorId(id);
		existente.setNome(novo.getNome());
		return repository.save(existente);
	}

	public void deletar(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Escala de miniatura com ID " + id + " não encontrada.");
		}
		repository.deleteById(id);
	}
}
