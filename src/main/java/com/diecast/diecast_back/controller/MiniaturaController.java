package com.diecast.diecast_back.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.diecast.diecast_back.dto.MiniaturaBaixaEstoqueDTO;
import com.diecast.diecast_back.dto.MiniaturaDTO;
import com.diecast.diecast_back.dto.MiniaturaFilterDTO;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
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
	
	@GetMapping("/{id}/imagem")
	public ResponseEntity<byte[]> getImagem(@PathVariable Long id) {
	    Miniatura miniatura = service.findById(id);

	    if (miniatura.getImagem() == null) {
	        throw new ResourceNotFoundException("Miniatura não possui imagem cadastrada");
	    }

	    return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(miniatura.getImagem());
	}
	
	@GetMapping("/similares/{id}")
	public ResponseEntity<List<Miniatura>> getSimilares(
	        @PathVariable Long id,
	        @RequestParam(defaultValue = "5") int limit) {

	    List<Miniatura> similares = service.findSimilarMinis(id, limit);

	    return ResponseEntity.ok(similares);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Miniatura insert(@RequestPart("miniatura") MiniaturaDTO dto, @RequestPart("imagem") MultipartFile imagem)
			throws IOException {

		Miniatura entity = service.fromDTO(dto);

		entity.setQuantidadeEstoque(dto.getQuantidadeEstoque());
		entity.setQuantidadeDisponivel(dto.getQuantidadeEstoque());
		entity.setQuantidadeEmGaragem((long) 0);		
		
		entity.setImagem(service.comprimirImagem(imagem));
		return service.insert(entity);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Miniatura> update(
	        @PathVariable Long id,
	        @RequestPart("miniatura") MiniaturaDTO dto,
	        @RequestPart(value = "imagem", required = false) MultipartFile imagem
	) throws IOException {

	    Miniatura entity = service.fromDTO(dto);
	    entity.setId(id);
	    
	    entity.setQuantidadeEstoque(dto.getQuantidadeEstoque());
		entity.setQuantidadeDisponivel(dto.getQuantidadeDisponivel());		
		entity.setQuantidadeEmGaragem(dto.getQuantidadeEmGaragem());

	    if (imagem != null && !imagem.isEmpty()) {
	        entity.setImagem(service.comprimirImagem(imagem));
	    }
	    entity = service.update(id, entity);
	    return ResponseEntity.ok(entity);
	}
	
	@PatchMapping("/{id}/baixa-estoque/{quantidade}")
	public ResponseEntity<Miniatura> baixarEstoque(
	        @PathVariable Long id,
	        @PathVariable Long quantidade) {
	    Miniatura entity = service.baixarEstoque(id, quantidade);
	    return ResponseEntity.ok(entity);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.delete(id);
	}
}
