package com.botflix.service;

import com.botflix.dto.ListaSugestoes;
import com.botflix.dto.SugestaoFilme;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiService {

    private static final String SYSTEM_PROMPT = """
            Você é o BotFlix, um especialista em cinema.
            O usuário descreve como está se sentindo e/ou o que quer assistir (humor, gênero, tema, clima).
            Coloque primeiro o filme que melhor combina com o pedido.
            Sugira filmes REAIS e conhecidos que combinem com o pedido.
            Para cada filme informe o título (preferencialmente o título original) e o ano de lançamento.
            Não invente filmes. Não repita filmes.
            """;

    private final ChatClient chatClient;
    private final int quantidade;

    public GeminiService(ChatClient.Builder builder,
                         @Value("${botflix.quantidade-filmes}") int quantidade) {
        this.chatClient = builder.defaultSystem(SYSTEM_PROMPT).build();
        this.quantidade = quantidade;
    }

    public List<SugestaoFilme> sugerirFilmes(String pedido) {
        ListaSugestoes lista = chatClient.prompt()
                .user(u -> u.text("Sugira {n} filmes para este pedido: {pedido}")
                        .param("n", quantidade)
                        .param("pedido", pedido))
                .call()
                .entity(ListaSugestoes.class);

        return lista == null || lista.filmes() == null ? List.of() : lista.filmes();
    }
}
