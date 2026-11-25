package com.botflix.dto;

import com.botflix.entity.Recomendacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/** Mesmos nomes de campos que o front já lê (estilo TMDB). */
public record FilmeResponse(
        Long id,
        String title,
        String overview,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("vote_average") Double voteAverage,
        String pedido,
        @JsonProperty("criado_em") LocalDateTime criadoEm) {

    public static FilmeResponse de(Recomendacao r) {
        return new FilmeResponse(
                r.getTmdbId(),
                r.getTitulo(),
                r.getSinopse(),
                r.getPosterPath(),
                r.getNotaMedia() == null ? 0.0 : r.getNotaMedia(),
                r.getPedido(),
                r.getCriadoEm());
    }
}
