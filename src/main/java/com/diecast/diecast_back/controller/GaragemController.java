package com.diecast.diecast_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.diecast.diecast_back.dto.ClienteGaragemDTO;
import com.diecast.diecast_back.dto.GaragemDTO;
import com.diecast.diecast_back.model.Garagem;
import com.diecast.diecast_back.service.GaragemService;

@RestController
@RequestMapping("/garagem")
public class GaragemController {

    private final GaragemService service;

    public GaragemController(GaragemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Garagem>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garagem> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ClienteGaragemDTO> findClientGarageById(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.buscarClienteComGaragem(clienteId));
    }

    @PostMapping
    public ResponseEntity<Garagem> insert(@RequestBody GaragemDTO dto) {
        return ResponseEntity.ok(service.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garagem> update(@PathVariable Long id, @RequestBody GaragemDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/entregue")
    public ResponseEntity<Void> deleteMiniInSystem(@PathVariable Long id) {
        service.deleteMiniInSystem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/desistencia")
    public ResponseEntity<Void> deleteMiniInGarage(@PathVariable Long id) {
        service.deleteMiniInGarage(id);
        return ResponseEntity.noContent().build();
    }
}