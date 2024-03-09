package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.SerieApiEntities
import fr.jaetan.jmedia.core.networking.models.toSerie
import fr.jaetan.jmedia.core.networking.models.toSeries
import fr.jaetan.jmedia.models.works.Serie
import fr.jaetan.jmedia.services.GlobalSettings
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object SerieApi : JMediaApi() {
    override val baseUrl = "https://api.themoviedb.org/3"
    override val authorization = GlobalSettings.ApiKeys.TheMovieDb.key

    suspend fun search(field: String): List<Serie> {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}/search/tv?language=fr-FR&query=${field.replace(" ", "%20")}")
        }

        val response = httpClient.get(url.build())
        return response.body<SerieApiEntities.SerieList>().toSeries()
    }

    suspend fun getDetails(id: Long): Serie {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}/tv/$id?language=fr-FR")
        }

        val response = httpClient.get(url.build())
        return response.body<SerieApiEntities.SerieDetails>().toSerie()
    }
}