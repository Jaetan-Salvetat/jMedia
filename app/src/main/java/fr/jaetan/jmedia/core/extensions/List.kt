package fr.jaetan.jmedia.core.extensions

fun <T: Any?> List<T>.removeNullValues(): List<T & Any> = filterNotNull()