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

import com.diecast.diecast_back.model.StatusMiniatura;
import com.diecast.diecast_back.service.StatusMiniaturaService;

@RestController
@RequestMapping("/status-miniatura")
public class StatusMiniaturaController {
	private final StatusMiniaturaService service;

	public StatusMiniaturaController(StatusMiniaturaService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMiniatura create(@RequestBody StatusMiniatura linha) {
		return service.criar(linha);
	}

	@GetMapping
	public List<StatusMiniatura> findAll() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public StatusMiniatura findById(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PutMapping("/{id}")
	public StatusMiniatura update(@PathVariable Long id, @RequestBody StatusMiniatura status) {
		return service.atualizar(id, status);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deletar(id);
	}
}
