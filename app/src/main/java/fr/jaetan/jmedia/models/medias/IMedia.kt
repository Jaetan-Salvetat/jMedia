package fr.jaetan.jmedia.models.medias

import fr.jaetan.jmedia.models.medias.shared.Image
import fr.jaetan.jmedia.models.medias.shared.MediaType
import org.mongodb.kbson.ObjectId

interface IMedia {
    val id: ObjectId
    val title: String
    val synopsis: String?
    val type: MediaType
    var isInLibrary: Boolean
    val image: Image?
    val rating: Double?
}

fun <T: IMedia> IMedia.equalTo(work: T): Boolean = title == work.title
        && synopsis == work.synopsis
        && type == work.type

@Suppress("UNCHECKED_CAST")
fun <T: IMedia> List<IMedia>.takeWhereEqualTo(work: T): T? = takeWhile { it.equalTo(work) }.firstOrNull() as T?