package fr.jaetan.jmedia.core.services

import android.content.Context
import fr.jaetan.jmedia.BuildConfig
import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import fr.jaetan.jmedia.core.realm.entities.AuthorEntity
import fr.jaetan.jmedia.core.realm.entities.DemographicEntity
import fr.jaetan.jmedia.core.realm.entities.GenreEntity
import fr.jaetan.jmedia.core.realm.entities.ImageEntity
import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.core.realm.repositories.AnimeRepository
import fr.jaetan.jmedia.core.realm.repositories.MangaRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object MainViewModel {
    val userSettingsModel = UserSettingsModel()
    val mangaRepository: MangaRepository
        get() = MangaRepository(realm)
    val animeRepository: AnimeRepository
        get() = AnimeRepository(realm)

    private val config = RealmConfiguration.Builder(schema = setOf(
        MangaEntity::class,
        AnimeEntity::class,
        ImageEntity::class,
        AuthorEntity::class,
        GenreEntity::class,
        DemographicEntity::class
    ))
    private lateinit var realm: Realm

    suspend fun initialize(context: Context) {
        if (BuildConfig.DEBUG) {
            config.deleteRealmIfMigrationNeeded()
        }

        config.schemaVersion(0)
        realm = Realm.open(config.build())

        userSettingsModel.initialize(context)
    }
}