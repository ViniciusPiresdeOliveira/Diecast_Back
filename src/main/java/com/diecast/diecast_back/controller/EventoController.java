package com.diecast.diecast_back.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.diecast.diecast_back.model.Evento;
import com.diecast.diecast_back.service.EventoService;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/eventos")
@RequiredArgsConstructor
public class EventoController {

	private final EventoService eventoService;
	
	@GetMapping
	public ResponseEntity<List<Evento>> findAll() {
	    List<Evento> eventos = eventoService.findAll();
	    return ResponseEntity.ok(eventos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Evento> findById(@PathVariable Long id) {
	    Evento evento = eventoService.findById(id);
	    return ResponseEntity.ok(evento);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Evento insert(@RequestPart("evento") Evento evento,
			@RequestPart(value = "imagens", required = false) List<MultipartFile> imagens) throws IOException {

		if (imagens != null && !imagens.isEmpty()) {
			eventoService.adicionarImagens(evento, imagens);
		}

		return eventoService.insert(evento);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    eventoService.delete(id);
	}
}