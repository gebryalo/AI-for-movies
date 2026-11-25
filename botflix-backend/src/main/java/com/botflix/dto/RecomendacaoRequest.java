package com.botflix.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecomendacaoRequest(
        @JsonAlias({"userPrompt", "prompt"})
        @NotBlank(message = "Descreva o que você quer assistir.")
        @Size(max = 500, message = "Texto muito longo.")
        String pedido) {
}
