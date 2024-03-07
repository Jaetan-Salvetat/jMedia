package fr.jaetan.jmedia.core.realm.repositories

import fr.jaetan.jmedia.core.realm.entities.BookEntity
import fr.jaetan.jmedia.extensions.log
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
                e.log("BookRepository().add")
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

    override suspend fun removeAll() {
        realm.write {
            realm.query<BookEntity>().find().forEach { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}