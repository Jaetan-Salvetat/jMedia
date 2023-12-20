package fr.jaetan.jmedia.core.models.works

import android.graphics.Bitmap
import fr.jaetan.jmedia.core.services.realm.entities.ImageEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.mongodb.kbson.ObjectId

@Serializable
data class Image(
    val id: ObjectId = ObjectId(),
    val imageUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String,
    @Transient var bitmap: Bitmap? = null
)

fun Image.toBdd(): ImageEntity = ImageEntity(
    id = id,
    imageUrl = imageUrl,
    smallImageUrl = smallImageUrl,
    largeImageUrl = largeImageUrl
)