package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.shared.Author
import fr.jaetan.jmedia.models.works.shared.Demographic
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Manga(
    override val title: String,
    override val synopsis: String?,
    override val image: Image,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override var isInLibrary: Boolean = false,
    override val type: WorkType = WorkType.Manga,

    val volumes: Int?,
    val status: Status,
    val authors: List<Author>,
    val genres: List<Genre>,
    val demographics: List<Demographic>,
): IWork

fun Manga.toBdd(): MangaEntity = MangaEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = status.name,
    rating = rating,
    image = image.toBdd(),
    authors = authors.toBdd(),
    genres = genres.toBdd(),
    demographics = demographics.toBdd()
)
