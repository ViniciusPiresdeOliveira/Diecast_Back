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

import com.diecast.diecast_back.model.LinhaMiniatura;
import com.diecast.diecast_back.service.LinhaMiniaturaService;

@RestController
@RequestMapping("/linhas-miniatura")
public class LinhaMiniaturaController {
	private final LinhaMiniaturaService service;

	public LinhaMiniaturaController(LinhaMiniaturaService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LinhaMiniatura create(@RequestBody LinhaMiniatura linha) {
		return service.criar(linha);
	}

	@GetMapping
	public List<LinhaMiniatura> findAll() {
		return service.listar();
	}

	@GetMapping("/{id}")
	public LinhaMiniatura findById(@PathVariable Long id) {
		return service.buscarPorId(id);
	}

	@PutMapping("/{id}")
	public LinhaMiniatura update(@PathVariable Long id, @RequestBody LinhaMiniatura linha) {
		return service.atualizar(id, linha);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deletar(id);
	}
}
