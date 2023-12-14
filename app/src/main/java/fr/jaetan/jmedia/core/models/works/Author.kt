package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.objectbox.AuthorEntity
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val id: Long = 0,
    val name: String
)

fun List<Author>.toBdd(): List<AuthorEntity> = map {
    AuthorEntity(
        name = it.name
    )
}