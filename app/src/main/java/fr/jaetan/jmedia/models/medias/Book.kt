package fr.jaetan.jmedia.models.medias

import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.models.medias.shared.Author
import fr.jaetan.jmedia.models.medias.shared.Genre
import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.MediaType
import fr.jaetan.jmedia.models.medias.shared.toBdd
import org.mongodb.kbson.ObjectId

data class Book(
    override val title: String,
    override val synopsis: String?,
    override val image: Image?,
    override val rating: Double?,
    override val id: ObjectId = ObjectId(),
    override val type: MediaType = MediaType.Book,
    override var isInLibrary: Boolean = false,

    val genres: List<Genre>,
    val authors: List<Author>,
    val publisher: String?,
    val ratingsCount: Int
) : IMedia

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