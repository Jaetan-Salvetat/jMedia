package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Status
import fr.jaetan.jmedia.models.works.shared.fromString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class SerieEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var title: String = ""
    var synopsis: String? = null
    var rating: Double? = null
    var ratingCount: Long = 0
    var image: String? = null
    var apiId: Long = 0
    var status: String = Status.Unknown.name
    var seasons: RealmList<SeasonEntity> = realmListOf()
    var genres: RealmList<GenreEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        synopsis: String?,
        rating: Double?,
        ratingCount: Long,
        apiId: Long,
        image: String?,
        genres: List<GenreEntity>,
        status: Status,
        seasons: List<SeasonEntity>
    ): this() {
        this.id = id
        this.title = title
        this.synopsis = synopsis
        this.rating = rating
        this.ratingCount = ratingCount
        this.apiId = apiId
        this.image = image
        this.genres = genres.toRealmList()
        this.status = status.name
        this.seasons = seasons.toRealmList()
    }
}

fun List<SerieEntity>.toSeries(): List<Serie> = map { it.toSerie() }

fun SerieEntity.toSerie(): Serie = Serie(
    id = id,
    title = title,
    synopsis = synopsis,
    rating = rating,
    apiId = apiId,
    image = image?.let {
        Image(
            imageUrl = it,
            smallImageUrl = it,
            largeImageUrl = it
        )
    },
    genres = genres.toGenres(),
    status = Status.fromString(status),
    ratingCount = ratingCount,
    seasons = seasons.toSeasons()
)