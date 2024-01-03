package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import fr.jaetan.jmedia.models.WorkType
import org.mongodb.kbson.ObjectId

data class Movie(
    val id: ObjectId = ObjectId(),
    override val title: String,
    override val synopsis: String?,
    override val image: Image,
    override val rating: Double?,
    override var isInLibrary: Boolean = false,
    override val type: WorkType = WorkType.Movie,

    val genres: List<Genre>,
    val ratingCounts: Long
): IWork

fun Movie.toBdd(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    synopsis = synopsis,
    image = image.toBdd(),
    rating = rating,
    genres = genres.toBdd(),
    ratingCounts = ratingCounts
)