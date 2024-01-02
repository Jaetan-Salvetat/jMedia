package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.models.WorkType

data class Book(
    override val title: String,
    override val synopsis: String?,
    override val image: Image,
    override val rating: Double?,
    override val type: WorkType = WorkType.Book,
    override var isInLibrary: Boolean = false,

    val genres: List<Genre>,
    val authors: List<Author>,
    val publisher: String?,
    val ratingsCount: Int
): IWork
