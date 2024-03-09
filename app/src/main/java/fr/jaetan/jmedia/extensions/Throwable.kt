package fr.jaetan.jmedia.extensions

import fr.jaetan.jmedia.services.Logger

fun Throwable.log() = Logger.e(this)

fun Throwable.log(tag: String) = Logger.e(this, tag = tag)