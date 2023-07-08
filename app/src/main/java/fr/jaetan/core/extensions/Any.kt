package fr.jaetan.core.extensions

fun Any?.isNotNull(): Boolean = this != null
fun Any?.isNull(): Boolean = this == null

fun Any?.toStringOrNull(): String? = if (this.isNotNull()) {
    this.toString()
} else {
    null
}