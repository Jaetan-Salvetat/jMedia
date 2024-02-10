package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.removeDuplicate
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.shared.Author
import fr.jaetan.jmedia.models.works.shared.Demographic
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.models.works.shared.fromString
import kotlinx.serialization.Serializable

class MangaApiEntities {
    @Serializable
     data class MangasApi(
         val data: List<MangaData>
     )
    @Serializable
     data class MangaData(
        val title: String,
        val synopsis: String?,
        val volumes: Int?,
        val status: String,
        val images: Images,
        val authors: List<Author>,
        val genres: List<Genre>,
        val demographics: List<Demographic>,
        val score: Double?
     )

    @Serializable
    data class Images(
        val webp: Image
    )
}

fun MangaApiEntities.MangasApi.toMangas(): List<Manga> = data.removeDuplicate().map {
    Manga(
        title = it.title.trim(),
        synopsis = it.synopsis?.trim(),
        volumes = it.volumes,
        status = Status.fromString(it.status, WorkType.Manga),
        rating = it.score,
        image = it.images.webp,
        authors = it.authors,
        genres = it.genres,
        demographics = it.demographics
    )
}