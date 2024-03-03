package fr.jaetan.jmedia.services

import android.util.Log
import fr.jaetan.jmedia.extensions.printDataClassToString

object Logger {
    fun d(message: String, tag: String = "testt") {
        try {
            Log.d(tag, message)
        } catch (_: Exception) {
            print(message)
        }
    }

    fun d(nbr: Long, tag: String = "testt") {
        try {
            Log.d(tag, nbr.toString())
        } catch (_: Exception) {
            print(nbr)
        }
    }

    fun d(obj: Any?, tag: String = "testt") {
        obj?.printDataClassToString(tag)
            ?: Log.d(tag, "null")
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