package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.removeDuplicate
import fr.jaetan.jmedia.models.medias.Movie
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.Status
import fr.jaetan.jmedia.models.medias.shared.fromString
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


fun MovieApiEntities.MovieList.toMovies(): List<Movie> = results.removeDuplicate().map { it.toMovie() }

fun MovieApiEntities.MovieDetail.toMovie(): Movie = Movie(
    title = title.trim(),
    synopsis = overview.trim().ifEmpty { null },
    image = Image(
        smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${backdropPath}",
        imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${backdropPath}",
        largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${backdropPath}"
    ),
    rating = voteAverage,
    ratingCounts = voteCount,
    apiId = id,
    genres = genres.toGenres(),
    releaseDate = try { LocalDate.parse(releaseDate) } catch (e: Exception) { null },
    status = Status.fromString(status, MediaType.Movie)
)

private fun List<MovieApiEntities.GenreData>.toGenres(): List<Genre> = map { Genre(name = it.name) }