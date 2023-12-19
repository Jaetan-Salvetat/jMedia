package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.objectbox.GenreEntity
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Long = 0,
    val name: String
)

fun List<Genre>.toBdd(): List<GenreEntity> = map {
    GenreEntity(
        id = it.id,
        name = it.name
    )
}