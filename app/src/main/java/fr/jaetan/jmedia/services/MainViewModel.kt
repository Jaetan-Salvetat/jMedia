package fr.jaetan.jmedia.services

import android.content.Context
import android.content.Intent
import fr.jaetan.jmedia.app.MainActivity
import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import fr.jaetan.jmedia.core.realm.entities.AuthorEntity
import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.core.realm.entities.DemographicEntity
import fr.jaetan.jmedia.core.realm.entities.GenreEntity
import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import fr.jaetan.jmedia.core.realm.entities.SeasonEntity
import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object MainViewModel {
    val userSettings = UserSettingsModel()

    private val realmConfig = RealmConfiguration.Builder(
        schema = setOf(
            // region Models
            MangaEntity::class,
            AnimeEntity::class,
            BookEntity::class,
            MovieEntity::class,
            SerieEntity::class,
            // endregion
            // region Sub models
            AuthorEntity::class,
            GenreEntity::class,
            DemographicEntity::class,
            SeasonEntity::class
            // endregion
        )
    )
    lateinit var realm: Realm

    suspend fun initialize(context: Context) {
        // Let it at first
        initializeSettings()
        userSettings.initialize(context)
    }

    private fun initializeSettings() {
        // if (!GlobalSettings.isInRelease) { realmConfig.deleteRealmIfMigrationNeeded() }
        realmConfig.deleteRealmIfMigrationNeeded()

        realmConfig.schemaVersion(0)
        realm = Realm.open(realmConfig.build())
    }

    /**
     * Restart MainActivity
     */
    fun restartApp(context: Context) {
        val activity = context as MainActivity?
        val intent = Intent(activity, MainActivity::class.java)

        activity?.finish()
        context.startActivity(intent)
    }
}