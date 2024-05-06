package fr.jaetan.jmedia.models.medias

import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.Season
import fr.jaetan.jmedia.models.medias.shared.Status
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Serie(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override var id: ObjectId = ObjectId(),
    override var isInLibrary: Boolean = false,
    override val type: MediaType = MediaType.Serie,

    val apiId: Long,
    val ratingCount: Long,
    val status: Status = Status.Unknown,
    val genres: List<Genre> = emptyList(),
    val seasons: List<Season> = emptyList()
) : IMedia

fun Serie.toBdd(): SerieEntity = SerieEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    rating = rating,
    apiId = apiId,
    ratingCount = ratingCount,
    status = status,
    image = image?.largeImageUrl,
    genres = genres.toBdd(),
    seasons = seasons.toBdd()
)
