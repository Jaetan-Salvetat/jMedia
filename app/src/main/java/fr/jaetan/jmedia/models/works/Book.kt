package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.models.works.shared.Author
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.WorkType
import fr.jaetan.jmedia.models.works.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Book(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override val type: WorkType = WorkType.Book,
    override var isInLibrary: Boolean = false,

    val genres: List<Genre>,
    val authors: List<Author>,
    val publisher: String?,
    val ratingsCount: Int
): IWork

fun Book.toBdd(): BookEntity = BookEntity(
    id = id,
    title = title,
    ratingsCount = ratingsCount,
    synopsis = synopsis,
    rating = rating,
    publisher = publisher,
    image = image?.largeImageUrl,
    authors = authors.toBdd(),
    genres = genres.toBdd()
)