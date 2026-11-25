# BotFlix Backend

Java 21 + Spring Boot + Spring AI (Gemini) + TMDB + PostgreSQL.

## Rodar
1. `docker compose up -d`
2. `export GEMINI_API_KEY=...`   (Google AI Studio)
3. `export TMDB_TOKEN=...`       (Read Access Token v4 do TMDB)
4. `mvn spring-boot:run`

## Endpoints
- `POST /api/recomendacoes`  body: `{ "userPrompt": "estou cansado e quero relaxar" }`
  -> `{ "results": [ { id, title, overview, poster_path, vote_average, pedido, criado_em } ] }`
- `GET /api/recomendacoes`   histórico (últimas 50), mesmo formato.

## Front
Coloque index.html, style.css, reset.css, index.js, BOTFLIX2.png e fundo2.png em
`src/main/resources/static/` para o Spring servir o site em http://localhost:8080
(ou abra o front por Live Server: o CORS já está liberado).
