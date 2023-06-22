package fr.jaetan.core.models.data.works

import android.graphics.Bitmap

interface IWork {
    val title: String
    val description: String
    val coverImageUrl: String
    var coverImageBitmap: Bitmap?
}