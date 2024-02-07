package fr.jaetan.jmedia.exceptions

class UnknownStatusException(status: String): Exception("Unknown work status (${status.ifEmpty { "empty" }})")