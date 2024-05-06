package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.MangaApiEntities
import fr.jaetan.jmedia.core.networking.models.toMangas
import fr.jaetan.jmedia.models.medias.Manga
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object MangaApi : JMediaApi() {
    override val baseUrl = "https://api.jikan.moe/v4/manga?limit=15"

    suspend fun search(field: String): List<Manga> {
        val url = URLBuilder().apply {
            takeFrom("$baseUrl&q=${field.replace(" ", "%20")}")
        }
        val response = httpClient.get(url.build())
        return response.body<MangaApiEntities.MangasApi>().toMangas()
    }
}