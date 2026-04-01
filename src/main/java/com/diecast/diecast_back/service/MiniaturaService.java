package com.diecast.diecast_back.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diecast.diecast_back.dto.MiniaturaDTO;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.EscalaMiniatura;
import com.diecast.diecast_back.model.MarcaMiniatura;
import com.diecast.diecast_back.model.Miniatura;
import com.diecast.diecast_back.model.StatusMiniatura;
import com.diecast.diecast_back.model.TipoMiniatura;
import com.diecast.diecast_back.repository.EscalaMiniaturaRepository;
import com.diecast.diecast_back.repository.MarcaMiniaturaRepository;
import com.diecast.diecast_back.repository.MiniaturaRepository;
import com.diecast.diecast_back.repository.StatusMiniaturaRepository;
import com.diecast.diecast_back.repository.TipoMiniaturaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MiniaturaService {
	@Autowired
    private MiniaturaRepository repository;
	@Autowired
	private MarcaMiniaturaRepository marcaRepository;

	@Autowired
	private TipoMiniaturaRepository tipoRepository;

	@Autowired
	private StatusMiniaturaRepository statusRepository;

	@Autowired
	private EscalaMiniaturaRepository escalaRepository;

    public List<Miniatura> findAll() {
        return repository.findAll();
    }

    public Miniatura findById(Long id) {
    	return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"Não é possível encontrar: Miniatura com ID " + id + " não encontrada."));
	}
    
    public Miniatura fromDTO(MiniaturaDTO dto) {

        Miniatura entity = new Miniatura();

        entity.setNome(dto.getNome());
        entity.setAno(dto.getAno());

        // 🔥 Marca
        entity.setMarca(
            marcaRepository.findById(dto.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"))
        );

        // 🔥 Status
        if (dto.getStatusId() != null) {
            entity.setStatus(
                statusRepository.findById(dto.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Status não encontrado"))
            );
        }

        // 🔥 Escala
        if (dto.getEscalaId() != null) {
            entity.setEscala(
                escalaRepository.findById(dto.getEscalaId())
                    .orElseThrow(() -> new RuntimeException("Escala não encontrada"))
            );
        }

        // 🔥 Tipos (ManyToMany)
        if (dto.getTiposIds() != null && !dto.getTiposIds().isEmpty()) {
            entity.setTipos(
                tipoRepository.findAllById(dto.getTiposIds())
            );
        }

        // 🔥 Imagem
        if (dto.getImagem() != null) {
            entity.setImagem(
                java.util.Base64.getDecoder().decode(dto.getImagem())
            );
        }

        return entity;
    }

    public Miniatura insert(Miniatura obj) {
        return repository.save(obj);
    }

    public Miniatura update(Long id, Miniatura obj) {
        try {
            Miniatura entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Miniatura não encontrada");
        }
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Miniatura não encontrada");
        }
        repository.deleteById(id);
    }

    private void updateData(Miniatura entity, Miniatura obj) {
        entity.setNome(obj.getNome());
        entity.setMarca(obj.getMarca());
        entity.setTipos(obj.getTipos());
        entity.setStatus(obj.getStatus());
        entity.setImagem(obj.getImagem());
        entity.setAno(obj.getAno());
        entity.setEscala(obj.getEscala());
    }
}
