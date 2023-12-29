package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.realm.entities.AuthorEntity
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
