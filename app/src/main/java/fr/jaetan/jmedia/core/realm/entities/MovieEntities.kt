package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.fromString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class MovieEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var title: String = ""
    var synopsis: String? = null
    var rating: Double? = null
    var ratingCounts: Long = 0
    var image: ImageEntity? = null
    var apiId: Long = 0
    var status: String = Status.Unknown.name
    var releaseDate: String? = null
    var genres: RealmList<GenreEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        synopsis: String?,
        rating: Double?,
        ratingCounts: Long,
        apiId: Long,
        image: ImageEntity,
        genres: List<GenreEntity>,
        status: Status,
        releaseDate: LocalDate?
    ): this() {
        this.id = id
        this.title = title
        this.synopsis = synopsis
        this.rating = rating
        this.ratingCounts = ratingCounts
        this.apiId = apiId
        this.image = image
        this.genres = genres.toRealmList()
        this.status = status.name
        this.releaseDate = releaseDate.toString()

    }
}

fun List<MovieEntity>.toMovies(): List<Movie> = map { it.toMovie() }

fun MovieEntity.toMovie(): Movie = Movie(
    id = id,
    title = title,
    synopsis = synopsis,
    rating = rating,
    ratingCounts = ratingCounts,
    apiId = apiId,
    image = image.toImage(),
    genres = genres.toGenres(),
    status = Status.fromString(status),
    releaseDate = LocalDate.parse(releaseDate)
)