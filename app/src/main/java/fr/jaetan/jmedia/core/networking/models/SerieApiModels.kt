package fr.jaetan.jmedia.core.networking.models

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
        val genreIds: List<Int>,
        val backdropPath: String?,
    )
}