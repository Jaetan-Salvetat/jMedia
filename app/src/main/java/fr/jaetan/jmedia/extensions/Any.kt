package fr.jaetan.jmedia.extensions

import fr.jaetan.jmedia.services.Logger

fun Any?.isNotNull(): Boolean = this != null

fun Any?.isNull(): Boolean = this == null

fun Any.printDataClassToString(tag: String = "testt") {
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
    Logger.d(result, tag)
}