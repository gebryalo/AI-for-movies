package com.botflix.controller;

import com.botflix.dto.ListaResponse;
import com.botflix.dto.RecomendacaoRequest;
import com.botflix.service.RecomendacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {

    private final RecomendacaoService service;

    public RecomendacaoController(RecomendacaoService service) {
        this.service = service;
    }

    /** Body: { "userPrompt": "..." } (ou "pedido"). Resposta: { "results": [...] } */
    @PostMapping
    public ListaResponse recomendar(@Valid @RequestBody RecomendacaoRequest request) {
        return new ListaResponse(service.recomendar(request.pedido()));
    }

    /** Histórico das últimas recomendações. */
    @GetMapping
    public ListaResponse historico() {
        return new ListaResponse(service.historico());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacao(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> erroGeral(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("erro", "Não foi possível gerar recomendações agora. Tente novamente."));
    }
}
