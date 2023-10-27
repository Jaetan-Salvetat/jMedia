package fr.jaetan.jmedia.core.models.works

data class Manga(
    val id: Int? = null,
    val title: String,
    val synopsis: String?,
    val volumes: Int?,
    val status: Status,
    val image: Image,
    val authors: List<Author>,
    val genres: List<Genre>,
    val demographics: List<Demographic>
)
