package fr.jaetan.jmedia.services

import android.util.Log
import fr.jaetan.jmedia.extensions.printDataClassToString

object Logger {
    fun d(obj: Any?, tag: String = "testt") {
        when (obj) {
            is String, is Long, is Boolean -> Log.d(tag, obj.toString())
            else -> obj?.printDataClassToString(tag)
                ?: Log.d(tag, "null")
        }
    }

    fun e(error: Throwable, message: String? = null, tag: String = "testt") {
        try {
            Log.e(tag, message ?: error.localizedMessage, error)
        } catch (_: Exception) {
            println("message: $message")
            println(error)
        }
    }
}