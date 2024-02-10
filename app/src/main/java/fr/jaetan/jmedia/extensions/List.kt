package fr.jaetan.jmedia.extensions

fun <T: Any?> List<T>.removeNullValues(): List<T & Any> = filterNotNull()

fun <T: Any> List<T>.removeDuplicate(): List<T> = toSet().toList()