package com.botflix.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recomendacoes")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String pedido;

    private Long tmdbId;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    private String posterPath;

    private Double notaMedia;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    protected Recomendacao() {
    }

    public Recomendacao(String pedido, Long tmdbId, String titulo, String sinopse,
                        String posterPath, Double notaMedia) {
        this.pedido = pedido;
        this.tmdbId = tmdbId;
        this.titulo = titulo;
        this.sinopse = sinopse;
        this.posterPath = posterPath;
        this.notaMedia = notaMedia;
    }

    public Long getId() { return id; }
    public String getPedido() { return pedido; }
    public Long getTmdbId() { return tmdbId; }
    public String getTitulo() { return titulo; }
    public String getSinopse() { return sinopse; }
    public String getPosterPath() { return posterPath; }
    public Double getNotaMedia() { return notaMedia; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
