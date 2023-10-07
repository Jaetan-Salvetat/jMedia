package fr.jaetan.jmedia.core.extensions

fun Any?.isNotNull(): Boolean = this != null

fun Any?.isNull(): Boolean = this == null