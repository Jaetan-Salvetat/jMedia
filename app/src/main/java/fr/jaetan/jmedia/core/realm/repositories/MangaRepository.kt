package fr.jaetan.jmedia.core.realm.repositories

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import fr.jaetan.jmedia.core.realm.entities.MangaEntity
import fr.jaetan.jmedia.services.Logger
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class MangaRepository(private val realm: Realm): IRepository<MangaEntity>() {
    override val all: Flow<ResultsChange<MangaEntity>>
        get() = realm.query<MangaEntity>().find().asFlow()

    override suspend fun add(work: MangaEntity) {
        realm.write {
            try {
                copyToRealm(work)
            } catch (e: Exception) {
                Logger.e(e, "MangaRepository().add")
                Firebase.crashlytics.recordException(e)
            }
        }
    }

    override suspend fun remove(work: MangaEntity) {
        realm.write {
            realm.query<MangaEntity>("id == $0", work.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}