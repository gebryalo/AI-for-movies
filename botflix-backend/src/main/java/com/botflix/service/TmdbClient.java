package com.botflix.service;

import com.botflix.dto.SugestaoFilme;
import com.botflix.dto.TmdbMovie;
import com.botflix.dto.TmdbSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class TmdbClient {

    private final RestClient restClient;

    public TmdbClient(@Value("${tmdb.base-url}") String baseUrl,
                      @Value("${tmdb.token}") String token) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    /** Busca o filme pelo título (e ano, se houver) e devolve o primeiro resultado. */
    public Optional<TmdbMovie> buscar(SugestaoFilme sugestao) {
        if (sugestao == null || sugestao.titulo() == null || sugestao.titulo().isBlank()) {
            return Optional.empty();
        }

        TmdbSearchResponse resposta = restClient.get()
                .uri(uri -> {
                    var b = uri.path("/search/movie")
                            .queryParam("query", sugestao.titulo())
                            .queryParam("language", "pt-BR")
                            .queryParam("include_adult", false);
                    if (sugestao.ano() != null) {
                        b.queryParam("year", sugestao.ano());
                    }
                    return b.build();
                })
                .retrieve()
                .body(TmdbSearchResponse.class);

        if (resposta == null || resposta.results() == null || resposta.results().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resposta.results().get(0));
    }
}
