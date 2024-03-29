package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.removeDuplicate
import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.shared.Demographic
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.models.works.shared.fromString
import kotlinx.serialization.Serializable

sealed class AnimeApiEntities {
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

fun AnimeApiEntities.AnimeApi.toAnimes(): List<Anime> = data.removeDuplicate().map {
    Anime(
        title = it.title.trim(),
        synopsis = it.synopsis?.trim(),
        image = it.images.webp,
        status = Status.fromString(it.status, WorkType.Anime),
        genres = it.genres,
        demographics = it.demographics,
        episodes = it.episodes,
        rating = it.score,
    )
}
