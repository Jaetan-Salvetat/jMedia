package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.shared.Image
import org.mongodb.kbson.ObjectId

interface IWork {
    val id: ObjectId
    val title: String
    val synopsis: String?
    val type: WorkType
    var isInLibrary: Boolean
    val image: Image
    val rating: Double?
}

fun <T: IWork> IWork.equalTo(work: T): Boolean = title == work.title
        && synopsis == work.synopsis
        && type == work.type
        && image == image
        && rating == rating

fun <T: IWork> List<IWork>.takeWhereEqualTo(work: T): T? = takeWhile { it.equalTo(work) }.firstOrNull() as T?