package com.diecast.diecast_back.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.TipoMiniatura;
import com.diecast.diecast_back.repository.TipoMiniaturaRepository;

import java.util.List;

@Service
public class TipoMiniaturaService {

	private final TipoMiniaturaRepository repository;

	public TipoMiniaturaService(TipoMiniaturaRepository repository) {
		this.repository = repository;
	}

	public TipoMiniatura criar(TipoMiniatura tipo) {
		if (repository.existsByNome(tipo.getNome())) {
			throw new DatabaseException("O nome '" + tipo.getNome() + "' já está cadacvcvstrado.");
		}
		return repository.save(tipo);
	}

	public List<TipoMiniatura> listar() {
		return repository.findAll();
	}

	public TipoMiniatura buscarPorId(Short id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Tipo não encontrado"));
	}

	public TipoMiniatura atualizar(Short id, TipoMiniatura novo) {
		TipoMiniatura existente = buscarPorId(id);
		existente.setNome(novo.getNome());
		return repository.save(existente);
	}

	public void deletar(Short id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Tipo de miniatura com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}
