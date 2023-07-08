package fr.jaetan.core.models.data.works

import android.graphics.Bitmap

interface IWork {
    val id: Int
    val title: String
    val description: String
    val coverImageUrl: String
    var coverImageBitmap: Bitmap?
    val authors: List<WorkAuthor>
}