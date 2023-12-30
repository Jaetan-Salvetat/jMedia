package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.models.WorkType
import org.mongodb.kbson.ObjectId

data class Anime(
    override val title: String,
    override val synopsis: String?,
    override val image: Image,
    override val rating: Double?,
    override val type: WorkType = WorkType.Anime,
    override var isInLibrary: Boolean = false,

    val id: ObjectId = ObjectId(),
    val status: Status,
    val genres: List<Genre>,
    val demographics: List<Demographic>,
    val episodes: Int?,
): IWork
