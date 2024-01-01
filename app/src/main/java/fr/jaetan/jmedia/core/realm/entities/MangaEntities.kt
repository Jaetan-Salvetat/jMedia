package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.Status
import fr.jaetan.jmedia.models.works.fromString
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class MangaEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var title: String = ""
    var synopsis: String? = null
    var volumes: Int? = null
    var status: String = Status.Unknown.name
    var score: Double? = null
    var image: ImageEntity? = ImageEntity()
    var authors: RealmList<AuthorEntity> = realmListOf()
    var genres: RealmList<GenreEntity> = realmListOf()
    var demographics: RealmList<DemographicEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        synopsis: String?,
        volumes: Int?,
        status: String,
        score: Double?,
        image: ImageEntity,
        authors: List<AuthorEntity>,
        genres: List<GenreEntity>,
        demographics: List<DemographicEntity>
    ): this() {
        this.id = id
        this.title = title
        this.synopsis = synopsis
        this.volumes = volumes
        this.status = status
        this.score = score
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
    status = Status.fromString(status),
    rating = score,
    image = image?.toImage() ?: Image(),
    authors = authors.toAuthors(),
    genres = genres.toGenres(),
    demographics = demographics.toDemographics()
)

