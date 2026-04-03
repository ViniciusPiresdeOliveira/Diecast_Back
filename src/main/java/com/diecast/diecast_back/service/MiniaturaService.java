package com.diecast.diecast_back.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public byte[] comprimirImagem(MultipartFile file) throws IOException {
		BufferedImage imagem = ImageIO.read(file.getInputStream());

		// 🔹 Redimensionar (ex: largura máxima 800px)
		int largura = 800;
		int altura = (int) (((double) largura / imagem.getWidth()) * imagem.getHeight());

		Image tmp = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		// 🔹 Comprimir (JPG com qualidade 70%)
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = writers.next();

		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(0.5f); // 🔥 ajuste aqui (0.5 - 0.8 ideal)

		writer.write(null, new IIOImage(resized, null, null), param);

		writer.dispose();
		ios.close();

		return baos.toByteArray();
	}

	public Miniatura fromDTO(MiniaturaDTO dto) {

		Miniatura entity = new Miniatura();

		entity.setNome(dto.getNome());
		entity.setAno(dto.getAno());

		// 🔥 Marca
		entity.setMarca(marcaRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new RuntimeException("Marca não encontrada")));

		// 🔥 Status
		if (dto.getStatusId() != null) {
			entity.setStatus(statusRepository.findById(dto.getStatusId())
					.orElseThrow(() -> new RuntimeException("Status não encontrado")));
		}

		// 🔥 Escala
		if (dto.getEscalaId() != null) {
			entity.setEscala(escalaRepository.findById(dto.getEscalaId())
					.orElseThrow(() -> new RuntimeException("Escala não encontrada")));
		}

		// 🔥 Tipos (ManyToMany)
		if (dto.getTiposIds() != null && !dto.getTiposIds().isEmpty()) {
			entity.setTipos(tipoRepository.findAllById(dto.getTiposIds()));
		}

		// 🔥 Imagem
//        if (dto.getImagem() != null) {
//            entity.setImagem(
//                java.util.Base64.getDecoder().decode(dto.getImagem())
//            );
//        }

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
		repository.deleteAll();
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
