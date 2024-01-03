package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Anime
import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.models.works.Status
import fr.jaetan.jmedia.models.works.fromString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class AnimeEntity(): RealmObject {
    var id: ObjectId = BsonObjectId()
    var title: String = ""
    var status: String = Status.Unknown.name
    var synopsis: String? = null
    var episodes: Int? = null
    var rating: Double? = null
    var image: ImageEntity? = ImageEntity()
    var genres: RealmList<GenreEntity> = realmListOf()
    var demographics: RealmList<DemographicEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        status: String,
        synopsis: String?,
        episodes: Int?,
        rating: Double?,
        image: ImageEntity,
        genres: List<GenreEntity>,
        demographics: List<DemographicEntity>
    ): this() {
        this.id = id
        this.title = title
        this.synopsis = synopsis
        this.episodes = episodes
        this.status = status
        this.rating = rating
        this.image = image
        this.genres = genres.toRealmList()
        this.demographics = demographics.toRealmList()
    }
}

// Converters
fun List<AnimeEntity>.toAnimes(): List<Anime> = map {it.toAnime() }

fun AnimeEntity.toAnime(): Anime = Anime(
    id = id,
    title = title,
    synopsis = synopsis,
    episodes = episodes,
    status = Status.fromString(status),
    rating = rating,
    image = image?.toImage() ?: Image(),
    genres = genres.toGenres(),
    demographics = demographics.toDemographics()
)