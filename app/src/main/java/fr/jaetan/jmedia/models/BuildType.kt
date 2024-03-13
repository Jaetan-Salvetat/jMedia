package fr.jaetan.jmedia.models

import fr.jaetan.jmedia.BuildConfig

enum class BuildType {
    Release,
    Staging,
    Demo,
    Debug;

    companion object {
        fun get(): BuildType = when (BuildConfig.BUILD_TYPE) {
            "release" -> Release
            "staging" -> Staging
            "demo" -> Demo
            else -> Debug
        }
    }
}