package fr.jaetan.jmedia.models.works.shared

import fr.jaetan.jmedia.core.realm.entities.GenreEntity
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Genre(
    val id: ObjectId = ObjectId(),
    val name: String
)

fun List<Genre>.toBdd(): List<GenreEntity> = map {
    GenreEntity(
        id = it.id,
        name = it.name
    )
}