package com.diecast.diecast_back.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.diecast.diecast_back.model.TipoMiniatura;
import com.diecast.diecast_back.service.TipoMiniaturaService;

import java.util.List;

@RestController
@RequestMapping("/tipos-miniatura")
public class TipoMiniaturaController {

    private final TipoMiniaturaService service;

    public TipoMiniaturaController(TipoMiniaturaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoMiniatura create(@RequestBody TipoMiniatura tipo) {
        return service.criar(tipo);
    }

    @GetMapping
    public List<TipoMiniatura> findAll() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TipoMiniatura findById(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public TipoMiniatura update(@PathVariable Long id, @RequestBody TipoMiniatura tipo) {
        return service.atualizar(id, tipo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deletar(id);
    }
}