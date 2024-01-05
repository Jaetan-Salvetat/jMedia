package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.MovieApiModels
import fr.jaetan.jmedia.core.networking.models.toMovies
import fr.jaetan.jmedia.models.works.Movie
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object MovieApi: JMediaApi() {
    override val baseUrl = "https://api.themoviedb.org/3"
    override val authorization = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MjJlZWI3Y2YwNzkyYTk5M2JiNzA3ZDVlZjk0MGFmOCIsInN1YiI6IjY1OTNiZjVkY2U0ZGRjNmQzODdlZWJjNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cOxaS8lMWOz2u94wQ3LFL261kYEaR5hMan4FHssv0T8"

    suspend fun search(field: String): List<Movie> {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}/search/movie?language=fr-FR&query=${field.replace(" ", "%20")}")
        }

        val response = httpClient.get(url.build())
        return response.body<MovieApiModels.MovieApi>().toMovies()
    }
}