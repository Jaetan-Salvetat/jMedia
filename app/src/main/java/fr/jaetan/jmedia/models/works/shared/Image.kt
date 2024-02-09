package fr.jaetan.jmedia.models.works.shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import fr.jaetan.jmedia.extensions.toHttpsPrefix
import fr.jaetan.jmedia.services.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.net.URL

@Serializable
data class Image(
    val imageUrl: String = "",
    val smallImageUrl: String = "",
    val largeImageUrl: String = "",
    @Transient var bitmap: Bitmap? = null
)

 suspend fun Image.generateBitmap() {
     bitmap = try {
         withContext(Dispatchers.IO) {
             val url = URL(imageUrl.toHttpsPrefix())
             val bitmap = BitmapFactory.decodeStream(url.openStream())

             Bitmap.createScaledBitmap(
                 bitmap,
                 bitmap.width / 2,
                 bitmap.height / 2,
                 false
             )
         }
     } catch (e: Exception) {
         Logger.e(e)
         null
     }
 }
