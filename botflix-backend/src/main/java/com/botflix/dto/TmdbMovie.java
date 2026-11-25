package com.botflix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TmdbMovie(
        Long id,
        String title,
        String overview,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("vote_average") Double voteAverage) {
}
