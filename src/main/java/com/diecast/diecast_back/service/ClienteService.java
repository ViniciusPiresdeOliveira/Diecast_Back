package com.diecast.diecast_back.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.Cliente;
import com.diecast.diecast_back.repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}

	public List<Cliente> search(String termo) {
		if (termo == null || termo.isBlank()) {
			return findAll();
		}
		return repository.search(termo.trim());
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
	}

	public Cliente insert(Cliente cliente) {

		if (repository.existsByTelefone(cliente.getTelefone())) {
			throw new DatabaseException("Telefone já cadastrado");
		}
		return repository.save(cliente);
	}

	public Cliente update(Long id, Cliente clienteAtualizado) {
		Cliente cliente = findById(id);
		
	    if (repository.existsByTelefoneAndIdNot(clienteAtualizado.getTelefone(), id)) {
	        throw new DatabaseException("Telefone já cadastrado");
	    }

		cliente.setNome(clienteAtualizado.getNome());
		cliente.setTelefone(clienteAtualizado.getTelefone());
		cliente.setCep(clienteAtualizado.getCep());
		cliente.setNumeroResidencia(clienteAtualizado.getNumeroResidencia());

		return repository.save(cliente);
	}

	public void delete(Long id) {
		Cliente cliente = findById(id);

		try {
			repository.delete(cliente);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(String.format(
					"Não é possível excluir %s, pois existem miniaturas vinculadas à sua garagem",
					cliente.getNome()));
		}
	}
}
