package fr.jaetan.jmedia.services

import android.content.Context
import fr.jaetan.jmedia.controllers.WorksController
import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import fr.jaetan.jmedia.core.realm.entities.AuthorEntity
import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.core.realm.entities.DemographicEntity
import fr.jaetan.jmedia.core.realm.entities.GenreEntity
import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import fr.jaetan.jmedia.core.realm.entities.SeasonEntity
import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import fr.jaetan.jmedia.core.realm.repositories.AnimeRepository
import fr.jaetan.jmedia.core.realm.repositories.BookRepository
import fr.jaetan.jmedia.core.realm.repositories.MangaRepository
import fr.jaetan.jmedia.core.realm.repositories.MovieRepository
import fr.jaetan.jmedia.core.realm.repositories.SerieRepository
import fr.jaetan.jmedia.models.GlobalSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object MainViewModel {
    val userSettings = UserSettingsModel()
    val mangaRepository by lazy { MangaRepository(realm) }
    val animeRepository by lazy { AnimeRepository(realm) }
    val bookRepository by lazy { BookRepository(realm) }
    val movieRepository by lazy { MovieRepository(realm) }
    val serieRepository by lazy { SerieRepository(realm) }
    val worksController = WorksController()

    private val realmConfig = RealmConfiguration.Builder(schema = setOf(
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
    ))
    private lateinit var realm: Realm

    suspend fun initialize(context: Context) {
        // Let it at first
        initializeSettings()

        worksController.initializeControllers()
        userSettings.initialize(context)
    }

    private fun initializeSettings() {
        if (!GlobalSettings.isInRelease) { realmConfig.deleteRealmIfMigrationNeeded() }

        realmConfig.schemaVersion(0)
        realm = Realm.open(realmConfig.build())
    }
}