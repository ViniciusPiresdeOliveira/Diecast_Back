package com.diecast.diecast_back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diecast.diecast_back.model.CondicaoMiniatura;
import com.diecast.diecast_back.service.CondicaoMiniaturaService;

@RestController
@RequestMapping("/condição-miniatura")
public class CondiçãoMiniaturaController {
	private final CondicaoMiniaturaService service;

	public CondiçãoMiniaturaController(CondicaoMiniaturaService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CondicaoMiniatura create(@RequestBody CondicaoMiniatura linha) {
		return service.criar(linha);
	}

	@GetMapping
	public List<CondicaoMiniatura> findAll() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public CondicaoMiniatura findById(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PutMapping("/{id}")
	public CondicaoMiniatura update(@PathVariable Long id, @RequestBody CondicaoMiniatura status) {
		return service.atualizar(id, status);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deletar(id);
	}
}
