package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.GithubApiEntities
import fr.jaetan.jmedia.services.GlobalSettings
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi


@OptIn(ExperimentalSerializationApi::class)
object GithubApi : JMediaApi() {
    override val authorization = GlobalSettings.ApiKeys.Github.key
    override val baseUrl = "https://api.github.com"

    suspend fun getLastVersion(): GithubApiEntities.Release {
        val url = URLBuilder().apply {
            takeFrom("$baseUrl/repos/Jaetan-Salvetat/jMedia/releases/latest")
        }
        val response = httpClient.get(url.build())

        return response.body()
    }
}
