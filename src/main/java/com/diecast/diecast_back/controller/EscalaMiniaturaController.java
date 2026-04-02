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

import com.diecast.diecast_back.model.EscalaMiniatura;
import com.diecast.diecast_back.service.EscalaMiniaturaService;

@RestController
@RequestMapping("/escalas-miniatura")
public class EscalaMiniaturaController {
	private final EscalaMiniaturaService service;

	public EscalaMiniaturaController(EscalaMiniaturaService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EscalaMiniatura create(@RequestBody EscalaMiniatura linha) {
		return service.criar(linha);
	}

	@GetMapping
	public List<EscalaMiniatura> findAll() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public EscalaMiniatura findById(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PutMapping("/{id}")
	public EscalaMiniatura update(@PathVariable Long id, @RequestBody EscalaMiniatura escala) {
		return service.atualizar(id, escala);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deletar(id);
	}
}
