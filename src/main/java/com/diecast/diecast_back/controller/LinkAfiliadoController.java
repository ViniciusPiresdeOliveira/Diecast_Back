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

import com.diecast.diecast_back.model.LinkAfiliado;
import com.diecast.diecast_back.service.LinkAfiliadoService;

@RestController
@RequestMapping("/link-afiliado")
public class LinkAfiliadoController {
	private final LinkAfiliadoService service;

	public LinkAfiliadoController(LinkAfiliadoService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LinkAfiliado create(@RequestBody LinkAfiliado linha) {
		return service.criar(linha);
	}

	@GetMapping
	public List<LinkAfiliado> findAll() {
		return service.listar();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deletar(id);
	}
}
