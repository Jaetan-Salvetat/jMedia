package fr.jaetan.core.models.data.works

import android.graphics.Bitmap

data class Manga(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val coverImageUrl: String,
    override var coverImageBitmap: Bitmap? = null,
    val vfState: WorkState? = null,
    val vfTomesCount: Int? = null,
    val voState: WorkState? = null,
    val voTomesCount: Int? = null,
    override val authors: List<WorkAuthor> = emptyList(),
    val genres: List<WorkGenre> = emptyList(),
): IWork
