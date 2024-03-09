package fr.jaetan.jmedia.models.works.shared

import fr.jaetan.jmedia.exceptions.UnknownStatusException
import fr.jaetan.jmedia.extensions.log

enum class Status {
    InProgress,
    Released,
    Upcoming,
    Pause,
    Unknown;

    companion object
}

fun Status.Companion.fromString(field: String, type: WorkType): Status = try {
    getStatusFromString(field, type)
} catch (e: Exception) {
    throwUnknownStatus(e)
}

private fun getStatusFromString(field: String, type: WorkType) = when {
    // Mangas only
    field == "Publishing" -> Status.InProgress
    field == "On Hiatus" -> Status.Pause

    // Animes only
    field == "Not yet aired" -> Status.Upcoming
    field == "Currently Airing" -> Status.InProgress

    // Movies only
    field == "Post Production" -> Status.Upcoming
    field == "Released" -> Status.Released
    field == "In Production" -> Status.InProgress

    // Series only
    field == "Ended" -> Status.Released
    field == "Returning Series" -> Status.InProgress

    // Generic
    field.contains("Finished") -> Status.Released
    field.isBlank() -> Status.Unknown
    else -> {
        Status.entries.find { it.name == field }
            ?: throw UnknownStatusException(field, type)
    }
}

private fun throwUnknownStatus(e: Exception): Status {
    e.log("Status.throwUnknownStatus()")

    return Status.Unknown
}