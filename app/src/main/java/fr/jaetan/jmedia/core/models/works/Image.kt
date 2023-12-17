package fr.jaetan.jmedia.core.models.works

import android.graphics.Bitmap
import fr.jaetan.jmedia.core.services.objectbox.ImageEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Image(
    val id: Long = 0,
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String,
    @Transient var bitmap: Bitmap? = null
)

fun Image.toBdd(): ImageEntity = ImageEntity(
    imageUrl = imageUrl,
    smallImageUrl = smallImageUrl,
    largeImageUrl = largeImageUrl
)