package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.models.works.shared.Image
import fr.jaetan.jmedia.models.works.shared.WorkType
import org.mongodb.kbson.ObjectId

interface IWork {
    val id: ObjectId
    val title: String
    val synopsis: String?
    val type: WorkType
    var isInLibrary: Boolean
    val image: Image?
    val rating: Double?
}

fun <T: IWork> IWork.equalTo(work: T): Boolean = title == work.title
        && synopsis == work.synopsis
        && type == work.type
        && image?.largeImageUrl == work.image?.largeImageUrl
        && rating == work.rating

fun <T: IWork> List<IWork>.takeWhereEqualTo(work: T): T? = takeWhile { it.equalTo(work) }.firstOrNull() as T?