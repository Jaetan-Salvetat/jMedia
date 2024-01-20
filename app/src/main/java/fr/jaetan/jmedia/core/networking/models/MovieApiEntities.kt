package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.fromString
import kotlinx.serialization.Serializable
import java.time.LocalDate

class MovieApiEntities {
    @Serializable
    data class MovieList(
        val results: List<MovieDetail>
    )

    @Serializable
    data class MovieDetail(
        val id: Long,
        val title: String,
        val overview: String,
        val voteAverage: Double,
        val voteCount: Long,
        val backdropPath: String?,

        val genres: List<GenreData> = emptyList(),
        val releaseDate: String = "",
        val status: String = ""
    )

    @Serializable
    data class GenreData(
        val name: String
    )
}


fun MovieApiEntities.MovieList.toMovies(): List<Movie> = results.map { it.toMovie() }

fun MovieApiEntities.MovieDetail.toMovie(): Movie = Movie(
    title = title,
    synopsis = overview.ifEmpty { null },
    image = Image(
        smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${backdropPath}",
        imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${backdropPath}",
        largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${backdropPath}"
    ),
    rating = voteAverage,
    ratingCounts = voteCount,
    apiId = id,
    genres = genres.toGenres(),
    releaseDate = LocalDate.parse(releaseDate),
    status = Status.fromString(status),

)

private fun List<MovieApiEntities.GenreData>.toGenres(): List<Genre> = map { Genre(name = it.name) }