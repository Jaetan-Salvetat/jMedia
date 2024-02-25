package fr.jaetan.jmedia.core.networking.models

import kotlinx.serialization.Serializable

sealed class GithubApiEntities {
    @Serializable
    data class Release(
        val tagName: String,
        val body: String,
        val assets: List<Asset>
    )

    @Serializable
    data class Asset(
        val browserDownloadUrl: String
    )
}