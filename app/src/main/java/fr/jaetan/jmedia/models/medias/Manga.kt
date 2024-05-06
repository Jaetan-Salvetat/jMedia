package fr.jaetan.jmedia.models.medias

import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.models.medias.shared.Author
import fr.jaetan.jmedia.models.medias.shared.Demographic
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.Status
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Manga(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override var isInLibrary: Boolean = false,
    override val type: MediaType = MediaType.Manga,

    val volumes: Int?,
    val status: Status,
    val authors: List<Author>,
    val genres: List<Genre>,
    val demographics: List<Demographic>,
) : IMedia

fun Manga.toBdd(): MangaEntity = MangaEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = status.name,
    rating = rating,
    image = image?.largeImageUrl,
    authors = authors.toBdd(),
    genres = genres.toBdd(),
    demographics = demographics.toBdd()
)
