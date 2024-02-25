package fr.jaetan.jmedia.core.networking.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed class GithubApiEntities {
    @Parcelize
    @Serializable
    data class Release(
        val tagName: String,
        val body: String,
        val assets: List<Asset>
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Asset(
        val browserDownloadUrl: String
    ) : Parcelable
}