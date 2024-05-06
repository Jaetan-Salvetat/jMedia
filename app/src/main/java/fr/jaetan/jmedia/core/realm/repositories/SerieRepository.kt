package fr.jaetan.jmedia.core.realm.repositories

import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import fr.jaetan.jmedia.extensions.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class SerieRepository(private val realm: Realm) : IRepository<SerieEntity>() {
    override val all: Flow<ResultsChange<SerieEntity>>
        get() = realm.query<SerieEntity>().find().asFlow()

    override suspend fun add(media: SerieEntity) {
        realm.write {
            try {
                copyToRealm(media)
            } catch (e: Exception) {
                e.log("SerieRepository().add")
            }
        }
    }

    override suspend fun remove(media: SerieEntity) {
        realm.write {
            realm.query<SerieEntity>("id == $0", media.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }

    override suspend fun removeAll() {
        realm.write {
            realm.query<SerieEntity>().find().forEach { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}