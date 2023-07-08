package fr.jaetan.core.models.data.works

import android.graphics.Bitmap

data class Manga(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val coverImageUrl: String,
    override var coverImageBitmap: Bitmap? = null,
    val status: WorkStatus = WorkStatus.Unknown,
    val currentTome: Int = 1,
    //val tomesCount: Int,
    //val tomesInFrench: Int,
    //val authors: List<WorkAuthor>,
    //val genres: List<WorkGenre> = emptyList(),
): IWork
