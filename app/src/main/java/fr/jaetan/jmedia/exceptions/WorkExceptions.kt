package fr.jaetan.jmedia.exceptions

import fr.jaetan.jmedia.models.works.shared.WorkType

class UnknownStatusException(status: String, type: WorkType, test: test = test()): Exception("Unknown work status (${status.ifEmpty { "empty" }}) for $type work type")

class test