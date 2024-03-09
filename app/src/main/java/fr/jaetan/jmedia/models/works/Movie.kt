package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.models.works.shared.toBdd
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

data class Movie(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double? = null,
    override val id: ObjectId = ObjectId(),
    override var isInLibrary: Boolean = false,
    override val type: WorkType = WorkType.Movie,

    val apiId: Long,
    val ratingCounts: Long = 0,
    val genres: List<Genre> = emptyList(),
    val status: Status = Status.Unknown,
    val releaseDate: LocalDate? = null
) : IWork

fun Movie.toBdd(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    image = image?.largeImageUrl,
    rating = rating,
    apiId = apiId,
    genres = genres.toBdd(),
    ratingCounts = ratingCounts,
    status = status,
    releaseDate = releaseDate
)