package fr.jaetan.jmedia.exceptions

import fr.jaetan.jmedia.models.medias.shared.MediaType

class UnknownStatusException(
    status: String,
    type: MediaType
) : Exception("Unknown work status (${status.ifEmpty { "empty" }}) for $type work type")