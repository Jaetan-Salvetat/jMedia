package fr.jaetan.jmedia.core.services.realm.repositories

import android.util.Log
import fr.jaetan.jmedia.core.services.realm.entities.AuthorEntity
import fr.jaetan.jmedia.core.services.realm.entities.MangaEntity
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
                Log.d("testt", realm.query<AuthorEntity>().count().find().toString())
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