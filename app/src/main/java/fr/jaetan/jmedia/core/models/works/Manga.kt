package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.realm.entities.MangaEntity
import org.mongodb.kbson.ObjectId

data class Manga(
    val id: ObjectId = ObjectId(),
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
    id = id,
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = status.name,
    image = image.toBdd(),
    authors = authors.toBdd(),
    genres = genres.toBdd(),
    demographics = demographics.toBdd()

)
