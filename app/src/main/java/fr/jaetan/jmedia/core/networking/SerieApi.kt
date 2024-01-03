package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.SerieApiModels
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object SerieApi: JMediaApi() {
    override val baseUrl = "https://api.themoviedb.org/3"
    override val authorization = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MjJlZWI3Y2YwNzkyYTk5M2JiNzA3ZDVlZjk0MGFmOCIsInN1YiI6IjY1OTNiZjVkY2U0ZGRjNmQzODdlZWJjNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cOxaS8lMWOz2u94wQ3LFL261kYEaR5hMan4FHssv0T8"

    suspend fun search(field: String): SerieApiModels.SerieList {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}/search/tv?language=fr-FR&query=${field.replace(" ", "%20")}")
        }

        val response = httpClient.get(url.build())
        return response.body()
    }
}