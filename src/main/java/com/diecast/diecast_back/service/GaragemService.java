package com.diecast.diecast_back.service;

import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diecast.diecast_back.dto.ClienteGaragemDTO;
import com.diecast.diecast_back.dto.GaragemDTO;
import com.diecast.diecast_back.dto.GaragemUpdateDTO;
import com.diecast.diecast_back.dto.MiniaturaGaragemDTO;
import com.diecast.diecast_back.dto.GenericDTO;
import com.diecast.diecast_back.exception.DatabaseException;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.Cliente;
import com.diecast.diecast_back.model.Garagem;
import com.diecast.diecast_back.model.Miniatura;
import com.diecast.diecast_back.repository.ClienteRepository;
import com.diecast.diecast_back.repository.GaragemRepository;
import com.diecast.diecast_back.repository.MiniaturaRepository;

@Service
public class GaragemService {

	private final GaragemRepository repository;
	private final MiniaturaRepository miniaturaRepository;
	private final ClienteRepository clienteRepository;

	public GaragemService(GaragemRepository repository, MiniaturaRepository miniaturaRepository,
			ClienteRepository clienteRepository) {
		this.repository = repository;
		this.miniaturaRepository = miniaturaRepository;
		this.clienteRepository = clienteRepository;
	}

	public List<Garagem> findAll() {
		return repository.findAll();
	}

	public Garagem findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Garagem não encontrada com id: " + id));
	}

	@Transactional
	public Garagem insert(GaragemDTO dto) {
		validarQuantidade(dto.getQuantidade());

		Miniatura miniatura = miniaturaRepository.findById(dto.getMiniaturaId()).orElseThrow(
				() -> new ResourceNotFoundException("Miniatura não encontrada com id: " + dto.getMiniaturaId()));

		Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(
				() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId()));

		if (repository.existsByClienteIdAndMiniaturaId(dto.getClienteId(), dto.getMiniaturaId())) {
			throw new DatabaseException(
					"Este cliente já possui esta miniatura na garagem. Atualize a quantidade em vez de criar um novo registro.");
		}

		validarEstoqueDisponivel(miniatura, dto.getQuantidade());

		miniatura.setQuantidadeDisponivel(miniatura.getQuantidadeDisponivel() - dto.getQuantidade());
		miniatura.setQuantidadeEmGaragem(miniatura.getQuantidadeEmGaragem() + dto.getQuantidade());
		miniaturaRepository.save(miniatura);

		Garagem obj = new Garagem();
		obj.setMiniatura(miniatura);
		obj.setCliente(cliente);
		obj.setQuantidade(dto.getQuantidade());

		return repository.save(obj);
	}

	@Transactional
	public Garagem update(Long id, GaragemUpdateDTO dto) {
		Garagem entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Garagem não encontrada com id: " + id));

		validarQuantidade(dto.getQuantidade());

		Miniatura miniatura = entity.getMiniatura();
		Long quantidadeAntiga = entity.getQuantidade();
		Long quantidadeNova = dto.getQuantidade();
		Long diferenca = quantidadeNova - quantidadeAntiga;

		Long limiteMaximo = miniatura.getQuantidadeDisponivel() + quantidadeAntiga;

		if (quantidadeNova > limiteMaximo) {
			String mensagem = limiteMaximo == 1 ? "Você pode ter no máximo 1 unidade desta miniatura na garagem"
					: "Você pode ter no máximo " + limiteMaximo + " unidades desta miniatura na garagem";
			throw new DatabaseException(mensagem);
		}

		if (diferenca > 0) {
			miniatura.setQuantidadeDisponivel(miniatura.getQuantidadeDisponivel() - diferenca);
			miniatura.setQuantidadeEmGaragem(miniatura.getQuantidadeEmGaragem() + diferenca);
		} else if (diferenca < 0) {
			Long devolucao = -diferenca;
			miniatura.setQuantidadeDisponivel(miniatura.getQuantidadeDisponivel() + devolucao);
			miniatura.setQuantidadeEmGaragem(miniatura.getQuantidadeEmGaragem() - devolucao);
		}

		miniaturaRepository.save(miniatura);

		entity.setQuantidade(quantidadeNova);
		return repository.save(entity);
	}

	@Transactional
	public void deleteMiniInSystem(Long id) {
		Garagem garagem = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Garagem não encontrada com id: " + id));

		Miniatura miniatura = garagem.getMiniatura();
		Long quantidade = garagem.getQuantidade();

		validarQuantidade(quantidade);

		boolean clienteDetemTodoEstoque = quantidade.equals(miniatura.getQuantidadeEstoque());

		repository.delete(garagem);

		if (clienteDetemTodoEstoque) {
			miniaturaRepository.delete(miniatura);
		} else {
			miniatura.setQuantidadeEstoque(miniatura.getQuantidadeEstoque() - quantidade);
			miniatura.setQuantidadeEmGaragem(subtrairSemNegativo(miniatura.getQuantidadeEmGaragem(), quantidade));
			miniaturaRepository.save(miniatura);
		}
	}

	@Transactional
	public void deleteMiniInGarage(Long id) {
		Garagem garagem = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Garagem não encontrada com id: " + id));

		Miniatura miniatura = garagem.getMiniatura();
		Long quantidade = garagem.getQuantidade();

		validarQuantidade(quantidade);

		repository.delete(garagem);

		miniatura.setQuantidadeEmGaragem(subtrairSemNegativo(miniatura.getQuantidadeEmGaragem(), quantidade));
		miniatura.setQuantidadeDisponivel(miniatura.getQuantidadeDisponivel() + quantidade);
		miniaturaRepository.save(miniatura);
	}

	public ClienteGaragemDTO buscarClienteComGaragem(Long clienteId) {
		List<Garagem> lista = repository.findByClienteIdWithMiniatura(clienteId);
		if (lista.isEmpty()) {
			throw new ResourceNotFoundException("Cliente não encontrado ou sem miniaturas na garagem");
		}

		Cliente cliente = lista.get(0).getCliente();

		List<MiniaturaGaragemDTO> miniaturas = lista.stream()
				.sorted(Comparator.comparing(Garagem::getDataCadastro).reversed()).map(g -> {
					Miniatura m = g.getMiniatura();
					MiniaturaGaragemDTO dto = new MiniaturaGaragemDTO();
					dto.setId(m.getId());
					dto.setNome(m.getNome());
					dto.setMarca(new GenericDTO(m.getMarca().getId(), m.getMarca().getNome()));
					dto.setTipos(m.getTipos().stream().map(t -> new GenericDTO(t.getId(), t.getNome())).toList());
					dto.setCondicao(new GenericDTO(m.getCondicao().getId(), m.getCondicao().getNome()));
					dto.setAno(m.getAno());
					dto.setEscala(new GenericDTO(m.getEscala().getId(), m.getEscala().getNome()));
					dto.setLinha(new GenericDTO(m.getLinha().getId(), m.getLinha().getNome()));
					dto.setValor(m.getValor());
					dto.setQuantidadeEmGaragem(g.getQuantidade());
					dto.setDataCadastro(g.getDataCadastro());
					return dto;
				}).toList();

		ClienteGaragemDTO response = new ClienteGaragemDTO();
		response.setId(cliente.getId());
		response.setNome(cliente.getNome());
		response.setMiniaturas(miniaturas);

		return response;
	}

	private void validarQuantidade(Long quantidade) {
		if (quantidade == null || quantidade <= 0) {
			throw new DatabaseException("Quantidade deve ser maior que zero");
		}
	}

	private void validarEstoqueDisponivel(Miniatura miniatura, Long quantidade) {
		if (miniatura.getQuantidadeDisponivel() < quantidade) {
			Long disponivel = miniatura.getQuantidadeDisponivel();
			String mensagem = disponivel == 1 ? "Existe apenas 1 miniatura disponível"
					: "Existem apenas " + disponivel + " miniaturas disponíveis";
			throw new DatabaseException(mensagem);
		}
	}

	private Long subtrairSemNegativo(Long atual, Long quantidade) {
		long base = atual == null ? 0L : atual;
		return Math.max(0L, base - quantidade);
	}
}