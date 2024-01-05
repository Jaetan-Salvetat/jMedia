package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.removeNullValues
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
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
}


fun MovieApiModels.MovieApi.toMovies(): List<Movie> = results.map {
    Movie(
        title = it.title,
        synopsis = it.overview,
        image = Image(
            smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${it.backdropPath}",
            imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${it.backdropPath}",
            largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${it.backdropPath}"
        ),
        rating = it.voteAverage,
        ratingCounts = it.voteCount
    )
}.removeNullValues()