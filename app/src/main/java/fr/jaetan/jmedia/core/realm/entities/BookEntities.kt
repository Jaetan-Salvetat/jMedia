package fr.jaetan.jmedia.core.realm.entities

import fr.jaetan.jmedia.models.works.Book
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class BookEntity(): RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()
    var title: String = ""
    var ratingsCount: Int = 0
    var synopsis: String? = null
    var rating: Double? = null
    var publisher: String? = null
    var image: ImageEntity? = ImageEntity()
    var authors: RealmList<AuthorEntity> = realmListOf()
    var genres: RealmList<GenreEntity> = realmListOf()

    constructor(
        id: ObjectId,
        title: String,
        ratingsCount: Int,
        synopsis: String?,
        rating: Double?,
        publisher: String?,
        image: ImageEntity,
        authors: List<AuthorEntity>,
        genres: List<GenreEntity>,
    ): this() {
        this.id = id
        this.title = title
        this.ratingsCount = ratingsCount
        this.synopsis = synopsis
        this.rating = rating
        this.publisher = publisher
        this.image = image
        this.authors = authors.toRealmList()
        this.genres = genres.toRealmList()
    }
}

fun List<BookEntity>.toBooks(): List<Book> = map { it.toBook() }

fun BookEntity.toBook(): Book = Book(
    id = id,
    title = title,
    ratingsCount = ratingsCount,
    synopsis = synopsis,
    rating = rating,
    publisher = publisher,
    image = image.toImage(),
    authors = authors.toAuthors(),
    genres = genres.toGenres()
)