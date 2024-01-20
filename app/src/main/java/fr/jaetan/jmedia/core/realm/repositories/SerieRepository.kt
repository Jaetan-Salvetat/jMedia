package fr.jaetan.jmedia.core.realm.repositories

import android.util.Log
import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class SerieRepository(private val realm: Realm): IRepository<SerieEntity>() {
    override val all: Flow<ResultsChange<SerieEntity>>
        get() = realm.query<SerieEntity>().find().asFlow()

    override suspend fun add(work: SerieEntity) {
        realm.write {
            try {
                copyToRealm(work)
            } catch (e: Exception) {
                Log.d("testt::SerieRepository::error", e.message ?: "null")
            }
        }
    }

    override suspend fun remove(work: SerieEntity) {
        realm.write {
            realm.query<SerieEntity>("id == $0", work.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}