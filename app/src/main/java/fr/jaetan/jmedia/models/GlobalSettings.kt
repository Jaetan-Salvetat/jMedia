package fr.jaetan.jmedia.models

object GlobalSettings {
    val buildType = BuildType.get()

    val isInRelease: Boolean
        get() = buildType.isInRelease
}
