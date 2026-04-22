package com.diecast.diecast_back.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.diecast.diecast_back.model.Evento;
import com.diecast.diecast_back.model.EventoImagem;
import com.diecast.diecast_back.repository.EventoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return eventoRepository.findAllWithImagens();
    }

    @Transactional(readOnly = true)
    public Evento findById(Long id) {
        return eventoRepository.findByIdWithImagens(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
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
		param.setCompressionQuality(0.4f); // 🔥 ajuste aqui (0.5 - 0.8 ideal)

		writer.write(null, new IIOImage(resized, null, null), param);

		writer.dispose();
		ios.close();

		return baos.toByteArray();
	}

    public void adicionarImagens(Evento evento, List<MultipartFile> imagens) throws IOException {

        List<EventoImagem> lista = new ArrayList<>();

        for (MultipartFile file : imagens) {

            EventoImagem img = new EventoImagem();

            // 🔥 AQUI entra a compressão
            byte[] imagemComprimida = comprimirImagem(file);

            img.setImagem(imagemComprimida);
            img.setEvento(evento);
            img.setDataCadastro(OffsetDateTime.now());

            lista.add(img);
        }

        evento.setImagens(lista);
    }
    
    public Evento insert(Evento evento) {

        OffsetDateTime now = OffsetDateTime.now();

        evento.setDataCadastro(now);
        evento.setDataAtualizacao(now);

        return eventoRepository.save(evento);
    }
    
    public void delete(Long id) {
        eventoRepository.deleteById(id);
    }
}