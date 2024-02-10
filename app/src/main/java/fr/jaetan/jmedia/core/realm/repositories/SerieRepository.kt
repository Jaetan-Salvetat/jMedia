package fr.jaetan.jmedia.core.realm.repositories

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import fr.jaetan.jmedia.core.realm.entities.SerieEntity
import fr.jaetan.jmedia.services.Logger
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
                Logger.e(e, "SerieRepository().add")
                Firebase.crashlytics.recordException(e)
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