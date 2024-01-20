package fr.jaetan.jmedia.core.realm.repositories

import android.util.Log
import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val realm: Realm): IRepository<MovieEntity>() {
    override val all: Flow<ResultsChange<MovieEntity>>
        get() = realm.query<MovieEntity>().find().asFlow()

    override suspend fun add(work: MovieEntity) {
        realm.write {
            try {
                copyToRealm(work)
            } catch (e: Exception) {
                Log.d("testt::MovieRepository::error", e.message ?: "null")
            }
        }
    }

    override suspend fun remove(work: MovieEntity) {
        realm.write {
            realm.query<MovieEntity>("id == $0", work.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}