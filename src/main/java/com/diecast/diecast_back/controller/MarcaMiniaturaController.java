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
    public MarcaMiniatura create(@RequestBody MarcaMiniatura marca) {
        return service.criar(marca);
    }

    @GetMapping
    public List<MarcaMiniatura> findAll() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MarcaMiniatura findById(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public MarcaMiniatura update(@PathVariable Long id, @RequestBody MarcaMiniatura marca) {
        return service.atualizar(id, marca);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deletar(id);
    }
}
