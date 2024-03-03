package fr.jaetan.jmedia.services

import fr.jaetan.jmedia.BuildConfig
import fr.jaetan.jmedia.models.BuildType

@Suppress("ConstPropertyName")
object GlobalSettings {
    private val buildType = BuildType.get()

    const val versionName = BuildConfig.VERSION_NAME
    const val versionCode = BuildConfig.VERSION_CODE

    val isInRelease: Boolean
        get() = buildType.isInRelease
}
