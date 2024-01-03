package fr.jaetan.jmedia.models.works.shared

enum class Status {
    InProgress,
    Finished,
    Upcoming,
    Pause,
    Unknown;

    companion object
}

fun Status.Companion.fromString(field: String): Status = when {

    // Manga only
    field == "Publishing" -> Status.InProgress
    field == "On Hiatus" -> Status.Pause

    // Anime only
    field == "Not yet aired" -> Status.Upcoming
    field == "Currently Airing" -> Status.InProgress

    // Generic
    field.contains("Finished") -> Status.Finished
    else -> Status.Unknown
}