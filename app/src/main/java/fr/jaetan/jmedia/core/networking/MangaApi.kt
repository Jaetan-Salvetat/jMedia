package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.models.works.Manga
import fr.jaetan.jmedia.core.networking.models.MangaApiModels
import fr.jaetan.jmedia.core.networking.models.toMangas
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

object MangaApi {
    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                namingStrategy = JsonNamingStrategy.SnakeCase
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
            }
            headers {
                contentType(ContentType.Application.Json)
                // append("ContentType", "application/json")
            }
        }
    }
    private const val baseUrl = "https://api.jikan.moe/v4"

    suspend fun search(field: String): List<Manga> {
        val url = URLBuilder().apply {
            takeFrom("$baseUrl/manga?q=${field.replace(" ", "%20")}")
        }
        val response = httpClient.get(url.build())
        return response.body<MangaApiModels.MangasApi>().toMangas()
    }
}