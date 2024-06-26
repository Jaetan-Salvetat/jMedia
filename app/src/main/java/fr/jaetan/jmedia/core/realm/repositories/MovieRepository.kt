package fr.jaetan.jmedia.core.realm.repositories

import fr.jaetan.jmedia.core.realm.entities.MovieEntity
import fr.jaetan.jmedia.extensions.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val realm: Realm) : IRepository<MovieEntity>() {
    override val all: Flow<ResultsChange<MovieEntity>>
        get() = realm.query<MovieEntity>().find().asFlow()

    override suspend fun add(media: MovieEntity) {
        realm.write {
            try {
                copyToRealm(media)
            } catch (e: Exception) {
                e.log("MovieRepository().add")
            }
        }
    }

    override suspend fun remove(media: MovieEntity) {
        realm.write {
            realm.query<MovieEntity>("id == $0", media.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }

    override suspend fun removeAll() {
        realm.write {
            realm.query<MovieEntity>().find().forEach { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}