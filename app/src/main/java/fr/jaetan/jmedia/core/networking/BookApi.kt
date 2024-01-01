package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.BookApiModels
import fr.jaetan.jmedia.extensions.printDataClassToString
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object BookApi: JMediaApi(null) {
    override val baseUrl = "https://www.googleapis.com/books/v1/volumes?maxResults=25&printType=books"

    suspend fun search(field: String) {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}&q=${field.replace(" ", "%20")}")
        }
        val response = httpClient.get(url.build())
        response.body<BookApiModels.Book>().printDataClassToString()
    }
}