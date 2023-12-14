package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.objectbox.MangaEntity

data class Manga(
    val id: Long = 0,
    val title: String,
    val synopsis: String?,
    val volumes: Int?,
    val status: Status,
    val image: Image,
    val authors: List<Author>,
    val genres: List<Genre>,
    val demographics: List<Demographic>
)

fun Manga.toBdd(): MangaEntity = MangaEntity(
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = status.name
).also {
    it.image.target = image.toBdd()
    it.authors.addAll(authors.toBdd())
    it.genres.addAll(genres.toBdd())
    it.demographics.addAll(demographics.toBdd())
}
