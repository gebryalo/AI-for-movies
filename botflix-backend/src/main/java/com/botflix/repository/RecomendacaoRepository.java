package com.botflix.repository;

import com.botflix.entity.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {
    List<Recomendacao> findTop50ByOrderByCriadoEmDescIdAsc();
}
