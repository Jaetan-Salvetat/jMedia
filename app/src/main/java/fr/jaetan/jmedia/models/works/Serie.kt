package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Season
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Serie(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override var isInLibrary: Boolean = false,
    override val type: WorkType = WorkType.Serie,

    val apiId: Long,
    val ratingCount: Long,
    val status: Status = Status.Unknown,
    val genres: List<Genre> = emptyList(),
    val seasons: List<Season> = emptyList()
): IWork

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
