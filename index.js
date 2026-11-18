const moodTextArea = document.getElementById('mood-textarea');
const searchButton = document.getElementById('search-button');

document.addEventListener('DOMContentLoaded', () => {
    setupEventListeners();
})

function setupEventListeners() {
    console.log('Event listeners set up');

    // Quando pressionar Enter
    moodTextArea.addEventListener('keypress', function (event) {
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            handleSearch();
        }
    });

    // Quando clicar no botão
    searchButton.addEventListener('click', handleSearch);

    // Mostrar no console enquanto digita
    moodTextArea.addEventListener('input', function (event) {
        console.log(event.target.value);
    });
}

async function handleSearch() {
    const mood = moodTextArea.value.trim();
    console.log("Enviado:", mood);

    if (!mood) {
        alert('Por favor, descreva como você está se sentindo.');
        return;
    }

    const response = await fetch('https://gebryalo.app.n8n.cloud/webhook/botflix', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userPrompt: mood })
    });

    const data = await response.json();
    console.log("Resposta do servidor:", data);

    if (data && data.results.length > 0) {
        const movie = data.results[0];
        const posterUrl = `https://image.tmdb.org/t/p/w500${movie.poster_path}`;

        const resultsDiv = document.getElementById("results");
        const moviesGrid = document.getElementById("movies-grid");

        resultsDiv.classList.add("show");

        moviesGrid.innerHTML = `<div class="movie-card">
                <div class="movie-poster">
                    <img src="${posterUrl}" alt="${movie.title}" />
                </div>
                <div class="movie-info">
                    <div class="movie-title">${movie.title}</div>
                    <div class="movie-overview">${movie.overview || "Sem descrição."}</div>
                    <div class="movie-rating">⭐ ${movie.vote_average.toFixed() || "N/A"} / 10</div>
                </div>
            </div>`;
    }
}
