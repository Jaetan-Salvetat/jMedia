package fr.jaetan.jmedia.core.realm.repositories

import android.util.Log
import fr.jaetan.jmedia.core.realm.entities.AnimeEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class AnimeRepository(private val realm: Realm): IRepository<AnimeEntity>() {
    override val all: Flow<ResultsChange<AnimeEntity>>
        get() = realm.query<AnimeEntity>().find().asFlow()

    override suspend fun add(work: AnimeEntity) {
        realm.write {
            try {
                copyToRealm(work)
            } catch (e: Exception) {
                Log.d("testt::AnimeRepository::error", e.message ?: "null")
            }
        }
    }

    override suspend fun remove(work: AnimeEntity) {
        realm.write {
            realm.query<AnimeEntity>("id == $0", work.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}