package fr.jaetan.jmedia.core.networking.models

import kotlinx.serialization.Serializable

class BookApiModels {
    @Serializable
    data class Book(
        val items: List<BookData>
    )

    @Serializable
    data class BookData(
        val volumeInfo: VolumeInfo
    )

    @Serializable
    data class VolumeInfo(
        val title: String,
        val authors: List<String> = emptyList(),
        val categories: List<String> = emptyList(),
        val description: String? = null,
        val imageLinks: ImageLinks? = null,
        val publisher: String? = null
    )

    @Serializable
    data class ImageLinks(
        val smallThumbnail: String,
        val thumbnail: String
    )
}