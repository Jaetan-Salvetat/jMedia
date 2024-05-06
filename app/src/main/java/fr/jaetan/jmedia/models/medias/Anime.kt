package fr.jaetan.jmedia.models.medias

import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import fr.jaetan.jmedia.models.medias.shared.Demographic
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.Status
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Anime(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override val type: MediaType = MediaType.Anime,
    override var isInLibrary: Boolean = false,

    val status: Status,
    val genres: List<Genre>,
    val demographics: List<Demographic>,
    val episodes: Int?,
) : IMedia

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
