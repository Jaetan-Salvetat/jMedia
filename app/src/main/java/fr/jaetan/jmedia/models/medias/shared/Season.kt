package fr.jaetan.jmedia.models.medias.shared

import fr.jaetan.jmedia.core.realm.entities.SeasonEntity
import org.mongodb.kbson.ObjectId

data class Season(
    val id: ObjectId = ObjectId(),
    val name: String,
    val episodeCount: Int,
    val seasonNumber: Int,
    val rating: Double,
    val synopsis: String
)

fun List<Season>.toBdd(): List<SeasonEntity> = map {
    SeasonEntity(
        id = it.id,
        name = it.name,
        episodeCount = it.episodeCount,
        seasonNumber = it.seasonNumber,
        rating = it.rating,
        synopsis = it.synopsis
    )
}