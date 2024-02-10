package fr.jaetan.jmedia.core.realm.repositories

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.services.Logger
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class BookRepository(private val realm: Realm): IRepository<BookEntity>() {
    override val all: Flow<ResultsChange<BookEntity>>
        get() = realm.query<BookEntity>().find().asFlow()

    override suspend fun add(work: BookEntity) {
        realm.write {
            try {
                copyToRealm(work)
            } catch (e: Exception) {
                Logger.e(e, "BookRepository().add")
                Firebase.crashlytics.recordException(e)
            }
        }
    }

    override suspend fun remove(work: BookEntity) {
        realm.write {
            realm.query<BookEntity>("id == $0", work.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}