package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.MovieApiEntities
import fr.jaetan.jmedia.core.networking.models.toMovie
import fr.jaetan.jmedia.core.networking.models.toMovies
import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.services.GlobalSettings
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object MovieApi : JMediaApi() {
    override val baseUrl = "https://api.themoviedb.org/3"
    override val authorization = GlobalSettings.ApiKeys.TheMovieDb.key

    suspend fun search(field: String): List<Movie> {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}/search/movie?language=fr-FR&query=${field.replace(" ", "%20")}")
        }

        val response = httpClient.get(url.build())
        return response.body<MovieApiEntities.MovieList>().toMovies()
    }

    suspend fun getDetail(id: Long): Movie {
        val url = URLBuilder().apply {
            takeFrom("${baseUrl}/movie/$id?language=fr-FR")
        }
        val response = httpClient.get(url.build())

        return response.body<MovieApiEntities.MovieDetail>().toMovie()
    }
}