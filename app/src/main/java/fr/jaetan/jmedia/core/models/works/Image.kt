package fr.jaetan.jmedia.core.models.works

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int? = null,
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
)
