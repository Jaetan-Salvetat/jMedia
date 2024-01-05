package fr.jaetan.jmedia.models.works.shared

import android.util.Log

enum class Status {
    InProgress,
    Released,
    Upcoming,
    Pause,
    Unknown;

    companion object
}

fun Status.Companion.fromString(field: String): Status = when {

    // Mangas only
    field == "Publishing" -> Status.InProgress
    field == "On Hiatus" -> Status.Pause

    // Animes only
    field == "Not yet aired" -> Status.Upcoming
    field == "Currently Airing" -> Status.InProgress

    // Movies only
    field == "Post Production" -> Status.Upcoming
    field == "Released" -> Status.Released

    // Series only
    field == "Ended" -> Status.Released
    field == "Returning Series" -> Status.InProgress

    // Generic
    field.contains("Finished") -> Status.Released
    else -> {
        try { Log.d("testt::status_unknown", field) }
        catch (_: Exception) {}
        Status.Unknown
    }
}