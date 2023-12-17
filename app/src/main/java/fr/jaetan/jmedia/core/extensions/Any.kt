package fr.jaetan.jmedia.core.extensions

import android.util.Log

fun Any?.isNotNull(): Boolean = this != null

fun Any?.isNull(): Boolean = this == null

fun Any.printDataClassToString(tag: String = "API LOGGER") {
    val excludeMembers = listOf("component", "hashCode", "toString")
    var result = "\n\ndata class ${this::class.simpleName}:"

    this::class.members.forEach {
        try {
            if (!it.name.containsList(excludeMembers)) {
                result += "\n${it.name} = ${it.call(this)}"
            }
        } catch (_: Exception) {}
    }

    result += "\n"

    try {
        Log.d(tag, result)
    } catch (_: Exception) {
        print(result)
    }
}