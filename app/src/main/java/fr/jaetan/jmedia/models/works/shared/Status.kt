package fr.jaetan.jmedia.models.works.shared

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

    // Generic
    field.contains("Finished") -> Status.Released
    else -> Status.Unknown
}