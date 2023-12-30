package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.Demographic
import fr.jaetan.jmedia.models.works.Genre
import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.models.works.Status
import fr.jaetan.jmedia.models.works.fromString
import kotlinx.serialization.Serializable

class AnimeApiModels {
    @Serializable
    data class AnimeApi(
        val data: List<AnimeData>
    )

    @Serializable
    data class AnimeData(
        val title: String,
        val synopsis: String?,
        val episodes: Int?,
        val status: String,
        val images: Images,
        val genres: List<Genre>,
        val demographics: List<Demographic>,
        val score: Double?
    )

    @Serializable
    data class Images(
        val webp: Image
    )
}

fun AnimeApiModels.AnimeApi.toAnimes(): List<Anime> = data.map {
    Anime(
        title = it.title,
        synopsis = it.synopsis,
        image = it.images.webp,
        status = Status.fromString(it.status),
        genres = it.genres,
        demographics = it.demographics,
        episodes = it.episodes,
        score = it.score,
    )
}
