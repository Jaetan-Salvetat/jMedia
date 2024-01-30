package fr.jaetan.jmedia.core.services

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
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
import fr.jaetan.jmedia.core.realm.entities.ImageEntity
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
import fr.jaetan.jmedia.models.WorkType
import fr.jaetan.jmedia.models.works.IWork
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.launch

object MainViewModel {
    val userSettingsModel = UserSettingsModel()
    val mangaRepository by lazy { MangaRepository(realm) }
    val animeRepository by lazy { AnimeRepository(realm) }
    val bookRepository by lazy { BookRepository(realm) }
    val movieRepository by lazy { MovieRepository(realm) }
    val serieRepository by lazy { SerieRepository(realm) }
    val controllers by lazy { controllersMap.values }

    val controllersMap = mapOf(
        WorkType.Manga to MangaController(),
        WorkType.Anime to AnimeController(),
        WorkType.Book to BookController(),
        WorkType.Movie to MovieController(),
        WorkType.Serie to SerieController()
    )
    private val realmConfig = RealmConfiguration.Builder(schema = setOf(
        // region Models
        MangaEntity::class,
        AnimeEntity::class,
        BookEntity::class,
        MovieEntity::class,
        SerieEntity::class,
        // endregion
        // region Sub models
        ImageEntity::class,
        AuthorEntity::class,
        GenreEntity::class,
        DemographicEntity::class,
        SeasonEntity::class
        // endregion
    ))
    private lateinit var realm: Realm

    @Suppress("UNCHECKED_CAST")
    fun getController(type: WorkType): IWorkController<IWork> = controllersMap[type] as IWorkController<IWork>

    suspend fun initialize(context: Context, lifeCycle: LifecycleCoroutineScope) {
        initializeSettings()
        initializeControllers(lifeCycle)
        userSettingsModel.initialize(context)
    }

    fun initializeControllers(lifeCycle: LifecycleCoroutineScope) {
        controllers.forEach {
            lifeCycle.launch {
                it.initializeFlow()
            }
        }
    }

    private fun initializeSettings() {
        if (!GlobalSettings.isInRelease) { realmConfig.deleteRealmIfMigrationNeeded() }

        realmConfig.schemaVersion(0)
        realm = Realm.open(realmConfig.build())


    }
}