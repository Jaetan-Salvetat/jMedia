package fr.jaetan.jmedia.core.networking.models

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
        val genreIds: List<Int>
    )
}