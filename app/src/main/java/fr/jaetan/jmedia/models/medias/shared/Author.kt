package fr.jaetan.jmedia.models.medias.shared

import fr.jaetan.jmedia.core.realm.entities.AuthorEntity
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Author(
    val id: ObjectId = ObjectId(),
    val name: String
)

fun List<Author>.toBdd(): List<AuthorEntity> = map {
    return@map AuthorEntity(
        id = it.id,
        name = it.name
    )
}
