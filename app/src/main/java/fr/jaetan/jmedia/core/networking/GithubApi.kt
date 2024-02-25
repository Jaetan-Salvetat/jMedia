package fr.jaetan.jmedia.core.networking

import fr.jaetan.jmedia.core.networking.models.GithubApiEntities
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
object GithubApi: JMediaApi() {
    override val authorization = "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"
    override val baseUrl = "ttps://api.github.com/repos/Jaetan-Salvet/jMedia"

    suspend fun getLastVersion(): GithubApiEntities.Release {
        val url = URLBuilder().apply {
            takeFrom("$baseUrl/releases/latest")
        }
        val response = httpClient.get(url.build())

        return response.body()
    }
}