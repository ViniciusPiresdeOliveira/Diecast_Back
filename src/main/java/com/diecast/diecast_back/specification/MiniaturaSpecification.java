package com.diecast.diecast_back.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;
import com.diecast.diecast_back.model.Miniatura;

public class MiniaturaSpecification {

    public static Specification<Miniatura> nomeContains(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isEmpty()) return null;
            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Miniatura> marcaIdEquals(Long marcaId) {
        return (root, query, cb) -> {
            if (marcaId == null) return null;
            return cb.equal(root.get("marca").get("id"), marcaId);
        };
    }

    public static Specification<Miniatura> anoEquals(Integer ano) {
        return (root, query, cb) -> {
            if (ano == null) return null;
            return cb.equal(root.get("ano"), ano);
        };
    }

    public static Specification<Miniatura> tipoIdEquals(Long tipoId) {
        return (root, query, cb) -> {
            if (tipoId == null) return null;
            return cb.equal(root.get("tipo").get("id"), tipoId);
        };
    }

    public static Specification<Miniatura> linhaIdEquals(Long linhaId) {
        return (root, query, cb) -> {
            if (linhaId == null) return null;
            return cb.equal(root.get("linha").get("id"), linhaId);
        };
    }

    public static Specification<Miniatura> statusEquals(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isEmpty()) return null;
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Miniatura> escalaEquals(String escala) {
        return (root, query, cb) -> {
            if (escala == null || escala.isEmpty()) return null;
            return cb.equal(root.get("escala"), escala);
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
}
