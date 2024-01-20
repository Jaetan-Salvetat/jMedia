package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.shared.Author
import fr.jaetan.jmedia.models.works.shared.Demographic
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.Season
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class ImageEntity(): RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()
    var imageUrl: String = ""
    var smallImageUrl: String = ""
    var largeImageUrl: String = ""

    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::image)
    val anime: RealmResults<AnimeEntity> by backlinks(AnimeEntity::image)
    val book: RealmResults<BookEntity> by backlinks(BookEntity::image)
    val movie: RealmResults<MovieEntity> by backlinks(MovieEntity::image)
    val serie: RealmResults<SerieEntity> by backlinks(SerieEntity::image)

    constructor(id: ObjectId, imageUrl: String, smallImageUrl: String, largeImageUrl: String) : this() {
        this.id = id
        this.imageUrl = imageUrl
        this.smallImageUrl = smallImageUrl
        this.largeImageUrl = largeImageUrl
    }
}

class AuthorEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class GenreEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class SeasonEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var episodeCount: Int = 0
    var seasonNumber: Int = 0
    var name: String = ""
    var rating: Double = .0
    var synopsis: String = ""

    constructor(
        id: ObjectId,
        name: String,
        episodeCount: Int,
        seasonNumber: Int,
        rating: Double,
        synopsis: String
    ): this() {
        this.id = id
        this.name = name
        this.episodeCount = episodeCount
        this.seasonNumber = seasonNumber
        this.rating = rating
        this.synopsis = synopsis
    }
}

class DemographicEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
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

fun List<SeasonEntity>.toSeasons(): List<Season> = map {
    Season(
        id = it.id,
        name = it.name,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber,
        rating = it.rating,
        synopsis = it.synopsis
    )
}

fun List<DemographicEntity>.toDemographics(): List<Demographic> = map {
    Demographic(
        id = it.id,
        name = it.name
    )
}