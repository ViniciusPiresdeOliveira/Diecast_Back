package com.diecast.diecast_back.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.diecast.diecast_back.model.MarcaMiniatura;
import com.diecast.diecast_back.service.MarcaMiniaturaService;

import java.util.List;

@RestController
@RequestMapping("/marcas-miniatura")
public class MarcaMiniaturaController {

    private final MarcaMiniaturaService service;

    public MarcaMiniaturaController(MarcaMiniaturaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarcaMiniatura create(@RequestBody MarcaMiniatura tipo) {
        return service.criar(tipo);
    }

    @GetMapping
    public List<MarcaMiniatura> findAll() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MarcaMiniatura findById(@PathVariable Short id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public MarcaMiniatura update(@PathVariable Short id, @RequestBody MarcaMiniatura tipo) {
        return service.atualizar(id, tipo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Short id) {
        service.deletar(id);
    }
}
