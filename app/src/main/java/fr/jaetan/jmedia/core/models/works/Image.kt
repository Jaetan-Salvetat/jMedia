package fr.jaetan.jmedia.core.models.works

import android.graphics.Bitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Image(
    val id: Int? = null,
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String,
    @Transient var bitmap: Bitmap? = null
)
