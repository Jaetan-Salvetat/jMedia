package fr.jaetan.jmedia.models.works.shared

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import fr.jaetan.jmedia.exceptions.UnknownStatusException

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
    // handle try / catch because 'Log' does not exist in  unit tests
    try { Log.e("testt::status_unknown", e.message.toString()) }
    catch (_: Exception) {}

    Firebase.crashlytics.recordException(e)

    return Status.Unknown
}