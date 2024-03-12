package fr.jaetan.jmedia.services

import fr.jaetan.jmedia.BuildConfig
import fr.jaetan.jmedia.models.BuildType

@Suppress("ConstPropertyName")
object GlobalSettings {
    private val buildType = BuildType.get()

    const val versionCode = BuildConfig.VERSION_CODE
    const val fullVersionName = BuildConfig.VERSION_NAME
    val versionName = when {
        fullVersionName.contains("-") -> fullVersionName.split("-").first()
        else -> fullVersionName
    }

    val isInDemo: Boolean
        get() = buildType == BuildType.Demo

    enum class ApiKeys(val key: String) {
        Github(BuildConfig.GITHUB_API_KEY),
        TheMovieDb(BuildConfig.THE_MOVIE_DB_API_KEY)
    }
}