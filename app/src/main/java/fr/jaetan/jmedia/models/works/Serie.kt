package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.shared.Genre
import fr.jaetan.jmedia.models.works.shared.Image
import org.mongodb.kbson.ObjectId

data class Serie(
    val id: ObjectId = ObjectId(),
    override val title: String,
    override val synopsis: String?,
    override val image: Image,
    override val rating: Double?,
    override var isInLibrary: Boolean = false,
    override val type: WorkType = WorkType.Serie,

    val ratingCount: Long,
    val genres: List<Genre> = emptyList(),
    val seasons: Int? = null
): IWork
