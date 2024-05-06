package fr.jaetan.jmedia.models.medias.shared

import fr.jaetan.jmedia.core.realm.entities.DemographicEntity
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
data class Demographic(
    val id: ObjectId = ObjectId(),
    val name: String
)

fun List<Demographic>.toBdd(): List<DemographicEntity> = map {
    return@map DemographicEntity(
        id = it.id,
        name = it.name
    )
}