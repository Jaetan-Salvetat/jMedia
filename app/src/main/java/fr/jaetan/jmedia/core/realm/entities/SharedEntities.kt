package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.shared.Author
import fr.jaetan.jmedia.models.works.shared.Demographic
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
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
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::authors)

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class GenreEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::genres)

    constructor(id: ObjectId, name: String): this() {
        this.id = id
        this.name = name
    }
}

class DemographicEntity(): RealmObject {
    @PrimaryKey var id: ObjectId = BsonObjectId()
    var name: String = ""
    val manga: RealmResults<MangaEntity> by backlinks(MangaEntity::demographics)

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

fun List<DemographicEntity>.toDemographics(): List<Demographic> = map {
    Demographic(
        id = it.id,
        name = it.name
    )
}