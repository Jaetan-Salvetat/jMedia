package fr.jaetan.jmedia.core.networking.models

import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.extensions.removeDuplicate
import fr.jaetan.jmedia.extensions.removeNullValues
import fr.jaetan.jmedia.extensions.toHttpsPrefix
import fr.jaetan.jmedia.models.works.Book
import fr.jaetan.jmedia.models.works.shared.Author
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import kotlinx.serialization.Serializable

class BookApiEntities {
    @Serializable
    data class BookApi(
        val items: List<BookData>
    )

    @Serializable
    data class BookData(
        val volumeInfo: VolumeInfo
    )

    @Serializable
    data class VolumeInfo(
        val title: String,
        val ratingsCount: Int = 0,
        val authors: List<String> = emptyList(),
        val categories: List<String> = emptyList(),
        val description: String? = null,
        val imageLinks: ImageLinks? = null,
        val publisher: String? = null,
        val averageRating: Double? = null
    )

    @Serializable
    data class ImageLinks(
        val smallThumbnail: String,
        val thumbnail: String
    )
}

fun BookApiEntities.BookApi.toBooks(): List<Book> = items.removeDuplicate().map { book ->
    val info = book.volumeInfo

    if (info.imageLinks.isNotNull()) {
        Book(
            title = info.title.trim(),
            synopsis = info.description?.trim(),
            image = info.imageLinks.toImage(),
            rating = info.averageRating,
            genres = info.categories.map { Genre(name = it) },
            authors = info.authors.map { Author(name = it) },
            publisher = info.publisher,
            ratingsCount = info.ratingsCount
        )
    } else {
        null
    }
}.removeNullValues()

private fun BookApiEntities.ImageLinks?.toImage(): Image = if (this.isNull()) {
    Image()
} else {
    Image(
        imageUrl = this!!.smallThumbnail.toHttpsPrefix(),
        smallImageUrl = smallThumbnail.toHttpsPrefix(),
        largeImageUrl = thumbnail.toHttpsPrefix()
    )
}