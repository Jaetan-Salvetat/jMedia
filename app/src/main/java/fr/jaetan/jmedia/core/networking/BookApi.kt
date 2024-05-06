package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.BookApiEntities
import fr.jaetan.jmedia.core.networking.models.toBooks
import fr.jaetan.jmedia.models.medias.Book
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object BookApi : JMediaApi(null) {
    override val baseUrl = "https://www.googleapis.com/books/v1/volumes?maxResults=15&printType=books"

    suspend fun search(field: String): List<Book> {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}&q=${field.replace(" ", "%20")}")
        }
        val response = httpClient.get(url.build())

        return response.body<BookApiEntities.BookApi>().toBooks()
    }
}