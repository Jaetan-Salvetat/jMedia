package fr.jaetan.jmedia.core.networking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import fr.jaetan.jmedia.core.networking.models.GithubApiEntities
import fr.jaetan.jmedia.services.GlobalSettings
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
private object GithubApi: JMediaApi() {
    override val authorization = "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"
    override val baseUrl = "https://api.github.com"

    suspend fun getLastVersion(): GithubApiEntities.Release {
        val url = URLBuilder().apply {
            takeFrom("$baseUrl/repos/Jaetan-Salvetat/jMedia/releases/latest")
        }
        val response = httpClient.get(url.build())

        return response.body()
    }
}

@Composable
fun rememberGithubRelease(): GithubApiEntities.Release? {
    if (!GlobalSettings.isInRelease) return null

    val scope = rememberCoroutineScope()
    var release by rememberSaveable { mutableStateOf(null as GithubApiEntities.Release?) }

    SideEffect {
        scope.launch {
            val tempRelease = GithubApi.getLastVersion()

            if (tempRelease.tagName != GlobalSettings.versionName) {
                release = tempRelease
            }
        }
    }

    return release
}