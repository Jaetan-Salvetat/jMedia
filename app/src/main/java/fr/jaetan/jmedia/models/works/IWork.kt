package fr.jaetan.jmedia.models.works

import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.shared.Image

interface IWork {
    val title: String
    val synopsis: String?
    val type: WorkType
    var isInLibrary: Boolean
    val image: Image
    val rating: Double?
}