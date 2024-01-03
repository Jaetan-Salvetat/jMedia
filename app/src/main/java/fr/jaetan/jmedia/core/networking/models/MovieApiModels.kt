package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.models.works.Genre
import kotlinx.serialization.Serializable

class MovieApiModels {
    @Serializable
    data class MovieApi(
        val results: List<MovieResult>
    )

    @Serializable
    data class MovieResult(
        val title: String,
        val overview: String,
        val voteAverage: Double,
        val voteCount: Long,
        val genreIds: List<Int>,
        val genres: MutableList<Genre> = mutableListOf()
    )

    @Serializable
    data class GenreApi(
        val genres: List<GenresData>
    )

    @Serializable
    data class GenresData(
        val id: Int,
        val name: String
    )
}

fun MovieApiModels.GenreApi.toGenres(): List<Genre> = genres.map {
    Genre(
        name = it.name
    )
}

fun MovieApiModels.MovieApi.setGenres(genres: List<MovieApiModels.GenresData>) {
    for (movieIndex in results.indices) {
        for (genreId in results[movieIndex].genreIds) {
            genres.find { it.id == genreId }?.let {
                results[movieIndex].genres.add(Genre(name = it.name))
            }
        }
    }
}