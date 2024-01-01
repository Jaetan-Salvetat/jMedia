package fr.jaetan.jmedia.core.realm.repositories

import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class MangaRepository(private val realm: Realm) {
    val all: Flow<ResultsChange<MangaEntity>>
        get() = realm.query<MangaEntity>().find().asFlow()

    suspend fun add(manga: MangaEntity) {
        realm.write {
            try {
                copyToRealm(manga)
            } catch (_: Exception) {}
        }
    }

    suspend fun remove(manga: MangaEntity) {
        realm.write {
            realm.query<MangaEntity>("id == $0", manga.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}