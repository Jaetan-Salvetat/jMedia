package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import fr.jaetan.jmedia.models.works.shared.Demographic
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.models.works.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Anime(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override val type: WorkType = WorkType.Anime,
    override var isInLibrary: Boolean = false,

    val status: Status,
    val genres: List<Genre>,
    val demographics: List<Demographic>,
    val episodes: Int?,
) : IWork

fun Anime.toBdd(): AnimeEntity = AnimeEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    episodes = episodes,
    status = status.name,
    rating = rating,
    image = image?.largeImageUrl,
    genres = genres.toBdd(),
    demographics = demographics.toBdd()
)
