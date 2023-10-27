package fr.jaetan.jmedia.core.models.works

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val id: Int? = null,
    val name: String
)
