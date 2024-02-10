package fr.jaetan.jmedia.services

import android.content.Context
import fr.jaetan.jmedia.controllers.AnimeController
import fr.jaetan.jmedia.controllers.BookController
import fr.jaetan.jmedia.controllers.IWorkController
import fr.jaetan.jmedia.controllers.MangaController
import fr.jaetan.jmedia.controllers.MovieController
import fr.jaetan.jmedia.controllers.SerieController
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
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.WorkType
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object MainViewModel {
    val userSettings = UserSettingsModel()
    val mangaRepository by lazy { MangaRepository(realm) }
    val animeRepository by lazy { AnimeRepository(realm) }
    val bookRepository by lazy { BookRepository(realm) }
    val movieRepository by lazy { MovieRepository(realm) }
    val serieRepository by lazy { SerieRepository(realm) }

    val controllersMap = mapOf(
        WorkType.Manga to MangaController(),
        WorkType.Anime to AnimeController(),
        WorkType.Book to BookController(),
        WorkType.Movie to MovieController(),
        WorkType.Serie to SerieController()
    )

    val worksSize: Int
        get() = controllersMap.values.map { it.localWorks.size }.reduce { acc, i -> acc + i }

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

    @Suppress("UNCHECKED_CAST")
    fun getController(type: WorkType): IWorkController<IWork> = controllersMap[type] as IWorkController<IWork>

    suspend fun initialize(context: Context) {
        // Let it at first
        initializeSettings()

        initializeControllers()
        userSettings.initialize(context)
    }

    private fun initializeSettings() {
        if (!GlobalSettings.isInRelease) { realmConfig.deleteRealmIfMigrationNeeded() }

        realmConfig.schemaVersion(0)
        realm = Realm.open(realmConfig.build())
    }

    private suspend fun initializeControllers() {
        controllersMap.forEach {
            it.value.initializeFlow()
        }
    }
}