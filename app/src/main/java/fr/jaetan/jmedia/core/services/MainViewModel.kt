package fr.jaetan.jmedia.core.services

import android.content.Context
import fr.jaetan.jmedia.core.services.realm.entities.AuthorEntity
import fr.jaetan.jmedia.core.services.realm.entities.DemographicEntity
import fr.jaetan.jmedia.core.services.realm.entities.GenreEntity
import fr.jaetan.jmedia.core.services.realm.entities.ImageEntity
import fr.jaetan.jmedia.core.services.realm.entities.MangaEntity
import fr.jaetan.jmedia.core.services.realm.repositories.MangaRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object MainViewModel {
    val userSettingsModel = UserSettingsModel()
    val mangaRepository: MangaRepository
        get() = MangaRepository(realm)

    private val config = RealmConfiguration.create(schema = setOf(
        MangaEntity::class,
        ImageEntity::class,
        AuthorEntity::class,
        GenreEntity::class,
        DemographicEntity::class
    ))
    private val realm = Realm.open(config)

    suspend fun initialize(context: Context) {
        userSettingsModel.initialize(context)
    }
}