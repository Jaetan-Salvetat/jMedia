package fr.jaetan.jmedia.models

import fr.jaetan.jmedia.BuildConfig

enum class BuildType {
    Release,
    Staging,
    Demo,
    Debug;

    val isInRelease: Boolean
        get() = this == Release

    companion object {
        fun get(): BuildType = when (BuildConfig.BUILD_TYPE) {
            "release" -> Release
            "staging" -> Staging
            "demo" -> Demo
            else -> Debug
        }
    }
}