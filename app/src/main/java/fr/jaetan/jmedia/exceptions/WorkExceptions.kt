@file:Suppress("MatchingDeclarationName")
package fr.jaetan.jmedia.exceptions

import fr.jaetan.jmedia.models.works.shared.WorkType

class UnknownStatusException(status: String, type: WorkType)
    : Exception("Unknown work status (${status.ifEmpty { "empty" }}) for $type work type")