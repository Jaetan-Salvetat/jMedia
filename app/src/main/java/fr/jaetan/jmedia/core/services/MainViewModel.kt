package fr.jaetan.jmedia.core.services

import android.annotation.SuppressLint
import android.content.Context
import fr.jaetan.jmedia.BuildConfig
import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import fr.jaetan.jmedia.core.realm.entities.AuthorEntity
import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.core.realm.entities.DemographicEntity
import fr.jaetan.jmedia.core.realm.entities.GenreEntity
import fr.jaetan.jmedia.core.realm.entities.ImageEntity
import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import fr.jaetan.jmedia.core.realm.repositories.AnimeRepository
import fr.jaetan.jmedia.core.realm.repositories.BookRepository
import fr.jaetan.jmedia.core.realm.repositories.MangaRepository
import fr.jaetan.jmedia.core.realm.repositories.MovieRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object MainViewModel {
    val userSettingsModel = UserSettingsModel()
    val mangaRepository by lazy { MangaRepository(realm) }
    val animeRepository by lazy { AnimeRepository(realm) }
    val bookRepository by lazy { BookRepository(realm) }
    val movieRepository by lazy { MovieRepository(realm) }

    private val config = RealmConfiguration.Builder(schema = setOf(
        // region Models
        MangaEntity::class,
        AnimeEntity::class,
        BookEntity::class,
        MovieEntity::class,
        // endregion
        // region Sub models
        ImageEntity::class,
        AuthorEntity::class,
        GenreEntity::class,
        DemographicEntity::class
        // endregion
    ))
    private lateinit var realm: Realm

    @SuppressLint("RedundantIfStatement")
    suspend fun initialize(context: Context) {
        if (BuildConfig.BUILD_TYPE != "release") { config.deleteRealmIfMigrationNeeded() }

        config.schemaVersion(0)
        realm = Realm.open(config.build())

        userSettingsModel.initialize(context)
    }
}