package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Season
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.fromString
import kotlinx.serialization.Serializable

class SerieApiModels {
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

fun SerieApiModels.SerieList.toSeries(): List<Serie> = results.map { it.toSerie() }

fun SerieApiModels.SerieDetails.toSerie(): Serie = Serie(
    title = name,
    synopsis = overview,
    image = Image(
        smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${backdropPath}",
        imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${backdropPath}",
        largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${backdropPath}"
    ),
    apiId = id,
    rating = voteAverage,
    ratingCount = voteCount,
    status = Status.fromString(status),
    genres = genres.toGenres(),
    seasons = seasons.toSeasons()
)

private fun List<SerieApiModels.GenreData>.toGenres(): List<Genre> = map { Genre(name = it.name) }
private fun List<SerieApiModels.SeasonData>.toSeasons(): List<Season> = map {
    Season(
        name = it.name,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber,
        rating = it.voteAverage,
        synopsis = it.overview
    )
}