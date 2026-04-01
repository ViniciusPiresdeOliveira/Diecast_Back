package com.diecast.diecast_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diecast.diecast_back.dto.MiniaturaDTO;
import com.diecast.diecast_back.model.Miniatura;
import com.diecast.diecast_back.service.MiniaturaService;

@RestController
@RequestMapping("/miniaturas")
public class MiniaturaController {
	@Autowired
	private MiniaturaService service;

	@GetMapping
	public ResponseEntity<List<Miniatura>> findAll() {
		List<Miniatura> list = service.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public Miniatura findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public Miniatura insert(@RequestBody MiniaturaDTO dto) {
	    Miniatura entity = service.fromDTO(dto);
	    return entity = service.insert(entity);
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
