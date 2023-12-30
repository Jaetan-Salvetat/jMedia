package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Author
import fr.jaetan.jmedia.models.works.Demographic
import fr.jaetan.jmedia.models.works.Genre
import fr.jaetan.jmedia.models.works.Image
import fr.jaetan.jmedia.models.works.Manga
import fr.jaetan.jmedia.models.works.Status
import fr.jaetan.jmedia.models.works.fromString
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ImageEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = ObjectId()
    var imageUrl: String = ""
    var smallImageUrl: String = ""
    var largeImageUrl: String = ""
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::image)

    constructor(id: ObjectId, imageUrl: String, smallImageUrl: String, largeImageUrl: String) : this() {
        this.id = id
        this.imageUrl = imageUrl
        this.smallImageUrl = smallImageUrl
        this.largeImageUrl = largeImageUrl
    }
}

class AuthorEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var name: String = ""
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::authors)

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class GenreEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var name: String = ""
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::genres)

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class DemographicEntity(): RealmObject {
    var id: ObjectId = ObjectId()
    var name: String = ""
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::demographics)

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

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
fun ImageEntity?.toImage(): Image = Image(
    imageUrl = this?.imageUrl ?: "",
    smallImageUrl = this?.smallImageUrl ?: "",
    largeImageUrl = this?.largeImageUrl ?: ""
)

fun List<AuthorEntity>.toAuthors(): List<Author> = map {
    Author(
        id = it.id,
        name = it.name
    )
}

fun List<GenreEntity>.toGenres(): List<Genre> = map {
    Genre(
        id = it.id,
        name = it.name
    )
}

fun List<DemographicEntity>.toDemographics(): List<Demographic> = map {
    Demographic(
        id = it.id,
        name = it.name
    )
}

fun List<MangaEntity>.toMangas(): List<Manga> = map {it.toManga() }

fun MangaEntity.toManga(): Manga = Manga(
    id = id,
    title = title,
    synopsis = synopsis,
    volumes = volumes,
    status = Status.fromString(status),
    rating = score,
    image = image!!.toImage(),
    authors = authors.toAuthors(),
    genres = genres.toGenres(),
    demographics = demographics.toDemographics()
)

