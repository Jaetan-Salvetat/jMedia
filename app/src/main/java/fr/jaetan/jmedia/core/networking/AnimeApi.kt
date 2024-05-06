package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.AnimeApiEntities
import fr.jaetan.jmedia.core.networking.models.toAnimes
import fr.jaetan.jmedia.models.medias.Anime
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object AnimeApi : JMediaApi() {
    override val baseUrl = "https://api.jikan.moe/v4/anime?limit=15"

    suspend fun search(field: String): List<Anime> {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}&q=${field.replace(" ", "%20")}")
        }
        val response = httpClient.get(url.build())
        return response.body<AnimeApiEntities.AnimeApi>().toAnimes()
    }
}