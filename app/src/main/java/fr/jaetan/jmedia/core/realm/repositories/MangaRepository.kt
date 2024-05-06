package fr.jaetan.jmedia.core.realm.repositories

import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.extensions.log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class MangaRepository(private val realm: Realm) : IRepository<MangaEntity>() {
    override val all: Flow<ResultsChange<MangaEntity>>
        get() = realm.query<MangaEntity>().find().asFlow()

    override suspend fun add(media: MangaEntity) {
        realm.write {
            try {
                copyToRealm(media)
            } catch (e: Exception) {
                e.log("MangaRepository().add")
            }
        }
    }

    override suspend fun remove(media: MangaEntity) {
        realm.write {
            realm.query<MangaEntity>("id == $0", media.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }

    override suspend fun removeAll() {
        realm.write {
            realm.query<MangaEntity>().find().forEach { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}