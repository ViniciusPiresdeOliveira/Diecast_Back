package com.diecast.diecast_back.service;

import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.MarcaMiniatura;
import com.diecast.diecast_back.repository.MarcaMiniaturaRepository;

import java.util.List;

@Service
public class MarcaMiniaturaService {

	private final MarcaMiniaturaRepository repository;

	public MarcaMiniaturaService(MarcaMiniaturaRepository repository) {
		this.repository = repository;
	}

	public MarcaMiniatura criar(MarcaMiniatura tipo) {
		if (repository.existsByNome(tipo.getNome())) {
			throw new DatabaseException("O nome '" + tipo.getNome() + "' já está cadastrado.");
		}
		return repository.save(tipo);
	}

	public List<MarcaMiniatura> listar() {
		return repository.findAll();
	}

	public MarcaMiniatura buscarPorId(Short id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Não é possível encontrar: Marca de miniatura com ID " + id + " não encontrada."));
	}

	public MarcaMiniatura atualizar(Short id, MarcaMiniatura novo) {
		MarcaMiniatura existente = buscarPorId(id);
		existente.setNome(novo.getNome());
		return repository.save(existente);
	}

	public void deletar(Short id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Marca de miniatura com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}
