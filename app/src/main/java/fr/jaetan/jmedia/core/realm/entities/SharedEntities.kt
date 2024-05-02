package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.medias.shared.Author
import fr.jaetan.jmedia.models.medias.shared.Demographic
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Season
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class AuthorEntity() : RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class GenreEntity() : RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class SeasonEntity() : RealmObject {
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

class DemographicEntity() : RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

// Converters
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