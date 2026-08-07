package com.diecast.diecast_back.specification;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import com.diecast.diecast_back.model.Miniatura;

public class MiniaturaSpecification {

    public static Specification<Miniatura> nomeContains(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isEmpty()) return null;
            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Miniatura> marcaIdIn(List<Long> marcaIds) {
        return (root, query, cb) -> {
            if (marcaIds == null || marcaIds.isEmpty()) {
                return null;
            }
            return root.get("marca").get("id").in(marcaIds);
        };
    }

    public static Specification<Miniatura> anoEquals(Integer ano) {
        return (root, query, cb) -> {
            if (ano == null) return null;
            return cb.equal(root.get("ano"), ano);
        };
    }

    public static Specification<Miniatura> tipoIdIn(List<Long> tipoIds) {
        return (root, query, cb) -> {
            if (tipoIds == null || tipoIds.isEmpty()) {
                return null;
            }

            // evita duplicidade por causa do join
            query.distinct(true);

            return root.join("tipos").get("id").in(tipoIds);
        };
    }

    public static Specification<Miniatura> linhaIdIn(List<Long> linhaIds) {
        return (root, query, cb) -> {
            if (linhaIds == null || linhaIds.isEmpty()) {
                return null;
            }
            return root.get("linha").get("id").in(linhaIds);
        };
    }

    public static Specification<Miniatura> condicaoIdIn(List<Long> statusIds) {
        return (root, query, cb) -> {
            if (statusIds == null || statusIds.isEmpty()) {
                return null;
            }
            return root.get("status").get("id").in(statusIds);
        };
    }

    public static Specification<Miniatura> escalaIdIn(List<Long> escalaIds) {
        return (root, query, cb) -> {
            if (escalaIds == null || escalaIds.isEmpty()) {
                return null;
            }
            return root.get("escala").get("id").in(escalaIds);
        };
    }

    public static Specification<Miniatura> precoGreaterThanOrEqual(BigDecimal valorMin) {
        return (root, query, cb) -> {
            if (valorMin == null) return null;
            return cb.greaterThanOrEqualTo(root.get("valor"), valorMin);
        };
    }

    public static Specification<Miniatura> precoLessThanOrEqual(BigDecimal valorMax) {
        return (root, query, cb) -> {
            if (valorMax == null) return null;
            return cb.lessThanOrEqualTo(root.get("valor"), valorMax);
        };
    }
    
    public static Specification<Miniatura> quantidadeDisponivelGreaterThanOrEqual(Integer qtd) {
        return (root, query, cb) -> {
            if (qtd == null) return null;
            return cb.greaterThanOrEqualTo(root.get("quantidadeDisponivel"), qtd);
        };
    }
    
    public static Specification<Miniatura> quantidadeDisponivelLessThanOrEqual(Integer qtd) {
        return (root, query, cb) -> {
            if (qtd == null) return null;
            return cb.lessThanOrEqualTo(root.get("quantidadeDisponivel"), qtd);
        };
    }
}
