package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.extensions.removeNullValues
import fr.jaetan.jmedia.models.works.Genre
import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.models.works.Movie
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
        val backdropPath: String?,
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

fun MovieApiModels.MovieApi.setGenres(genres: List<MovieApiModels.GenresData>) {
    for (movieIndex in results.indices) {
        for (genreId in results[movieIndex].genreIds) {
            genres.find { it.id == genreId }?.let {
                results[movieIndex].genres.add(Genre(name = it.name))
            }
        }
    }
}

fun MovieApiModels.MovieApi.toMovies(): List<Movie> = results.map {
    if (it.backdropPath.isNull()) return@map null

    Movie(
        title = it.title,
        synopsis = it.overview,
        image = Image(
            smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${it.backdropPath}",
            imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${it.backdropPath}",
            largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${it.backdropPath}"
        ),
        genres = it.genres,
        rating = it.voteAverage,
        ratingCounts = it.voteCount
    )
}.removeNullValues()