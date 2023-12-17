package fr.jaetan.jmedia.core.models.works

import fr.jaetan.jmedia.core.services.objectbox.DemographicEntity
import kotlinx.serialization.Serializable

@Serializable
data class Demographic(
    val id: Long = 0,
    val name: String
)

fun List<Demographic>.toBdd(): List<DemographicEntity> = map {
    DemographicEntity(
        name = it.name
    )
}