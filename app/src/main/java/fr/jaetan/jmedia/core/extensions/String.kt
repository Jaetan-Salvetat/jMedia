package fr.jaetan.jmedia.core.extensions

fun String.containsList(list: List<String>): Boolean {
    list.forEach {
        if (this.contains(it)) {
            return true
        }
    }

    return false
}