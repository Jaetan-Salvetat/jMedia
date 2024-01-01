package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.MangaApiModels
import fr.jaetan.jmedia.core.networking.models.toMangas
import fr.jaetan.jmedia.models.works.Manga
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object MangaApi: JMediaApi() {
    override val baseUrl = "https://api.jikan.moe/v4/manga"

    suspend fun search(field: String): List<Manga> {
        val url = URLBuilder().apply {
            takeFrom("$baseUrl?q=${field.replace(" ", "%20")}")
        }
        val response = httpClient.get(url.build())
        return response.body<MangaApiModels.MangasApi>().toMangas()
    }
}