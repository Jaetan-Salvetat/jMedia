package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.models.WorkType

interface IWork {
    val title: String
    val synopsis: String?
    val type: WorkType
    var isInLibrary: Boolean
    val image: Image
}