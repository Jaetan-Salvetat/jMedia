package fr.jaetan.jmedia.core.models.works

enum class Status {
    Publishing,
    Finished,
    Unknown;

    companion object
}

fun Status.Companion.fromString(field: String): Status = when (field) {
    "Publishing" -> Status.Publishing
    "Finished" -> Status.Finished
    else -> Status.Unknown
}