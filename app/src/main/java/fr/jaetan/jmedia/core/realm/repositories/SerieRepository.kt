package fr.jaetan.jmedia.core.realm.repositories

import android.util.Log
import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class SerieRepository(private val realm: Realm) {
    val all: Flow<ResultsChange<SerieEntity>>
        get() = realm.query<SerieEntity>().find().asFlow()

    suspend fun add(manga: SerieEntity) {
        realm.write {
            try {
                copyToRealm(manga)
            } catch (e: Exception) {
                Log.d("testt::MovieRepository::error", e.message ?: "null")
            }
        }
    }

    suspend fun remove(manga: SerieEntity) {
        realm.write {
            realm.query<SerieEntity>("id == $0", manga.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}