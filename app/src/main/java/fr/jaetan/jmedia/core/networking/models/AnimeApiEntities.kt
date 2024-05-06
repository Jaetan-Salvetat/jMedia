package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.removeDuplicate
import fr.jaetan.jmedia.models.medias.Anime
import fr.jaetan.jmedia.models.medias.shared.Demographic
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.Status
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.fromString
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
        status = Status.fromString(it.status, MediaType.Anime),
        genres = it.genres,
        demographics = it.demographics,
        episodes = it.episodes,
        rating = it.score,
    )
}
