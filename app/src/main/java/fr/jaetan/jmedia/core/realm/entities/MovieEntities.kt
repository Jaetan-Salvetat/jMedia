package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Movie
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class MovieEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var title: String = ""
    var synopsis: String? = null
    var rating: Double? = null
    var ratingCounts: Long = 0
    var image: ImageEntity? = null
    var genres: RealmList<GenreEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        synopsis: String?,
        rating: Double?,
        ratingCounts: Long,
        image: ImageEntity,
        genres: List<GenreEntity>
    ): this() {
        this.id = id
        this.title = title
        this.synopsis = synopsis
        this.rating = rating
        this.ratingCounts = ratingCounts
        this.image = image
        this.genres = genres.toRealmList()

    }
}

fun List<MovieEntity>.toMovies(): List<Movie> = map { it.toMovie() }

fun MovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    synopsis = synopsis,
    rating = rating,
    ratingCounts = ratingCounts,
    image = image.toImage(),
    genres = genres.toGenres()
)