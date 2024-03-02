package fr.jaetan.jmedia.services

import fr.jaetan.jmedia.BuildConfig
import fr.jaetan.jmedia.models.BuildType

object GlobalSettings {
    val buildType = BuildType.get()
    val versionName = BuildConfig.VERSION_NAME
    val versionCode = BuildConfig.VERSION_CODE

    val isInRelease: Boolean
        get() = buildType.isInRelease
}
