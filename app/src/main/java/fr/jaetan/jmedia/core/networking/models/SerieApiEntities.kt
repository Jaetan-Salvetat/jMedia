package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.removeDuplicate
import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Season
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.models.works.shared.fromString
import kotlinx.serialization.Serializable

class SerieApiEntities {
    @Serializable
    data class SerieList(
        val results: List<SerieDetails>
    )

    @Serializable
    data class SerieDetails(
        val id: Long,
        val name: String,
        val overview: String,
        val voteAverage: Double,
        val voteCount: Long,
        val backdropPath: String?,
        val status: String = "",
        val genres: List<GenreData> = emptyList(),
        val seasons: List<SeasonData> = emptyList()
    )

    @Serializable
    data class GenreData(
        val name: String
    )

    @Serializable
    data class SeasonData(
        val name: String,
        val episodeCount: Int,
        val seasonNumber: Int,
        val voteAverage: Double,
        val overview: String
    )
}

fun SerieApiEntities.SerieList.toSeries(): List<Serie> = results.removeDuplicate().map { it.toSerie() }

fun SerieApiEntities.SerieDetails.toSerie(): Serie = Serie(
    title = name.trim(),
    synopsis = overview.trim().ifEmpty { null },
    image = Image(
        smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${backdropPath}",
        imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${backdropPath}",
        largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${backdropPath}"
    ),
    apiId = id,
    rating = voteAverage,
    ratingCount = voteCount,
    status = Status.fromString(status, WorkType.Serie),
    genres = genres.toGenres(),
    seasons = seasons.toSeasons()
)

private fun List<SerieApiEntities.GenreData>.toGenres(): List<Genre> = map { Genre(name = it.name) }
private fun List<SerieApiEntities.SeasonData>.toSeasons(): List<Season> = map {
    Season(
        name = it.name,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber,
        rating = it.voteAverage,
        synopsis = it.overview
    )
}