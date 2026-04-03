package com.diecast.diecast_back.specification;

import org.springframework.data.jpa.domain.Specification;
import com.diecast.diecast_back.model.Miniatura;

public class MiniaturaSpecification {
	 public static Specification<Miniatura> nomeContains(String nome) {
	        return (root, query, cb) -> 
	            nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
	    }

	    public static Specification<Miniatura> marcaIdEquals(Long marcaId) {
	        return (root, query, cb) -> 
	            marcaId == null ? null : cb.equal(root.get("marca").get("id"), marcaId);
	    }

	    public static Specification<Miniatura> anoEquals(Integer ano) {
	        return (root, query, cb) -> 
	            ano == null ? null : cb.equal(root.get("ano"), ano);
	    }
}
