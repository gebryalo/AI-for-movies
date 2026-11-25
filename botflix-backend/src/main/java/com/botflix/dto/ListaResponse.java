package com.botflix.dto;

import java.util.List;

/** Envelope { "results": [...] } esperado pelo front (data.results). */
public record ListaResponse(List<FilmeResponse> results) {
}
