package fr.jaetan.core.models.data.works

import android.graphics.Bitmap

data class Manga(
    override val title: String,
    override val description: String,
    override val coverImageUrl: String,
    override var coverImageBitmap: Bitmap? = null,
    val currentTome: Int = 1,
    val tomesCount: Int? = null,
    val tomesInFrench: Int? = null,
    val authors: List<WorkAuthor>? = null,
    val status: WorkStatus = WorkStatus.Unknown,
    val genres: List<WorkGenre> = emptyList(),
): IWork
