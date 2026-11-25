package com.botflix.service;

import com.botflix.dto.FilmeResponse;
import com.botflix.dto.SugestaoFilme;
import com.botflix.entity.Recomendacao;
import com.botflix.repository.RecomendacaoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RecomendacaoService {

    private final GeminiService gemini;
    private final TmdbClient tmdb;
    private final RecomendacaoRepository repository;

    public RecomendacaoService(GeminiService gemini,
                               TmdbClient tmdb,
                               RecomendacaoRepository repository) {
        this.gemini = gemini;
        this.tmdb = tmdb;
        this.repository = repository;
    }

    /** 1) Gemini sugere títulos  2) TMDB completa os dados  3) salva  4) devolve (na ordem do Gemini). */
    public List<FilmeResponse> recomendar(String pedido) {
        List<SugestaoFilme> sugestoes = gemini.sugerirFilmes(pedido);

        Set<Long> jaAdicionados = new HashSet<>();
        List<Recomendacao> encontrados = sugestoes.stream()
                .map(tmdb::buscar)
                .flatMap(Optional::stream)
                .filter(m -> jaAdicionados.add(m.id()))
                .map(m -> new Recomendacao(pedido, m.id(), m.title(), m.overview(),
                        m.posterPath(), m.voteAverage()))
                .toList();

        return repository.saveAll(encontrados).stream()
                .map(FilmeResponse::de)
                .toList();
    }

    public List<FilmeResponse> historico() {
        return repository.findTop50ByOrderByCriadoEmDescIdAsc().stream()
                .map(FilmeResponse::de)
                .toList();
    }
}
