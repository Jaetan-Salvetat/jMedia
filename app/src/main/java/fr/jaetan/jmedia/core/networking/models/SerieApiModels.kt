package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import kotlinx.serialization.Serializable

class SerieApiModels {
    @Serializable
    data class SerieList(
        val results: List<SerieListResult>
    )

    @Serializable
    data class SerieListResult(
        val id: Long,
        val name: String,
        val overview: String,
        val voteAverage: Double,
        val voteCount: Long,
        val backdropPath: String?,
        val genres: MutableList<Genre> = mutableListOf()
    )
}

fun SerieApiModels.SerieList.toSerie(): List<Serie> = results.map {
    Serie(
        title = it.name,
        synopsis = it.overview,
        image = Image(
            smallImageUrl = "https://image.tmdb.org/t/p/w150_and_h225_bestv2/${it.backdropPath}",
            imageUrl = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${it.backdropPath}",
            largeImageUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/${it.backdropPath}"
        ),
        rating = it.voteAverage,
        ratingCount = it.voteCount
    )
}