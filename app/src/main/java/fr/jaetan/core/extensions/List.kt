package fr.jaetan.core.extensions

fun <T: Any?> List<T>.removeNull(): List<T & Any> = filterNotNull()
