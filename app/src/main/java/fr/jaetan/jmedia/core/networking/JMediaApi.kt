package fr.jaetan.jmedia.core.networking

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

@OptIn(ExperimentalSerializationApi::class)
abstract class JMediaApi(private val namingStrategy: JsonNamingStrategy? = JsonNamingStrategy.SnakeCase) {
    protected abstract val baseUrl: String

    protected val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                namingStrategy = this@JMediaApi.namingStrategy
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
}