package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.medias.Manga
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.Status
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.fromString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class MangaEntity() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var synopsis: String? = null
    var volumes: Int? = null
    var status: String = Status.Unknown.name
    var rating: Double? = null
    var image: String? = null
    var authors: RealmList<AuthorEntity> = realmListOf()
    var genres: RealmList<GenreEntity> = realmListOf()
    var demographics: RealmList<DemographicEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        synopsis: String?,
        volumes: Int?,
        status: String,
        rating: Double?,
        image: String?,
        authors: List<AuthorEntity>,
        genres: List<GenreEntity>,
        demographics: List<DemographicEntity>
    ): this() {
        this.id = id
        this.title = title
        this.synopsis = synopsis
        this.volumes = volumes
        this.status = status
        this.rating = rating
        this.image = image
        this.authors = authors.toRealmList()
        this.genres = genres.toRealmList()
        this.demographics = demographics.toRealmList()
    }
}

// Converters
fun List<MangaEntity>.toMangas(): List<Manga> = map {it.toManga() }

fun MangaEntity.toManga(): Manga = Manga(
    id = id,
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = Status.fromString(status, MediaType.Manga),
    rating = rating,
    image = image?.let {
        Image(
            imageUrl = it,
            smallImageUrl = it,
            largeImageUrl = it
        )
    },
    authors = authors.toAuthors(),
    genres = genres.toGenres(),
    demographics = demographics.toDemographics(),
    isInLibrary = true
)

