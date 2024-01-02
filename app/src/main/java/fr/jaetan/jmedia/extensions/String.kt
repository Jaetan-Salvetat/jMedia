package fr.jaetan.jmedia.extensions

fun String.containsList(list: List<String>): Boolean {
    list.forEach {
        if (this.contains(it)) {
            return true
        }
    }

    return false
}

fun String.toHttpsPrefix(): String = when {
    this.startsWith("http://")  -> replace("http://", "https://")
    this.startsWith("https://")  -> this
    else -> "https://$this"
}