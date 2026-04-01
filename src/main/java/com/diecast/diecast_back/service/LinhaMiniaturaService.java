package com.diecast.diecast_back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.LinhaMiniatura;
import com.diecast.diecast_back.repository.LinhaMiniaturaRepository;

@Service
public class LinhaMiniaturaService {

	private final LinhaMiniaturaRepository repository;

	public LinhaMiniaturaService(LinhaMiniaturaRepository repository) {
		this.repository = repository;
	}

	public LinhaMiniatura criar(LinhaMiniatura tipo) {
		if (repository.existsByNome(tipo.getNome())) {
			throw new DatabaseException("O nome '" + tipo.getNome() + "' já está cadastrado.");
		}
		return repository.save(tipo);
	}

	public List<LinhaMiniatura> listar() {
		return repository.findAll();
	}

	public LinhaMiniatura buscarPorId(Short id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Não é possível encontrar: Linha de miniatura com ID " + id + " não encontrada."));
	}

	public LinhaMiniatura atualizar(Short id, LinhaMiniatura novo) {
		LinhaMiniatura existente = buscarPorId(id);
		existente.setNome(novo.getNome());
		return repository.save(existente);
	}

	public void deletar(Short id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(
					"Não é possível deletar: Linha de miniatura com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}