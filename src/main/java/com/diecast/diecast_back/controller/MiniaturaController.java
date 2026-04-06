package com.diecast.diecast_back.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.diecast.diecast_back.dto.MiniaturaDTO;
import com.diecast.diecast_back.dto.MiniaturaFilterDTO;
import com.diecast.diecast_back.model.Miniatura;
import com.diecast.diecast_back.service.MiniaturaService;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/miniaturas")
public class MiniaturaController {
	@Autowired
	private MiniaturaService service;

	@PostMapping("/filtro")
	public ResponseEntity<Page<Miniatura>> filtrar(@RequestBody MiniaturaFilterDTO filtro) {

	    PageRequest pageable = PageRequest.of(filtro.getPage(), filtro.getSize());

	    Page<Miniatura> result = service.findAllWithFilters(filtro, pageable);

	    return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public Miniatura findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Miniatura insert(@RequestPart("miniatura") MiniaturaDTO dto, @RequestPart("imagem") MultipartFile imagem)
			throws IOException {

		Miniatura entity = service.fromDTO(dto);

		entity.setImagem(service.comprimirImagem(imagem));
		return service.insert(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Miniatura> update(@PathVariable Long id, @RequestBody Miniatura obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok(obj);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}
}
