package fr.jaetan.jmedia.core.realm.repositories

import android.util.Log
import fr.jaetan.jmedia.core.realm.entities.BookEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class BookRepository(private val realm: Realm) {
    val all: Flow<ResultsChange<BookEntity>>
        get() = realm.query<BookEntity>().find().asFlow()

    suspend fun add(book: BookEntity) {
        realm.write {
            try {
                copyToRealm(book)
            } catch (e: Exception) {
                Log.d("testt::AnimeRepository::error", e.message ?: "null")
            }
        }
    }

    suspend fun remove(book: BookEntity) {
        realm.write {
            realm.query<BookEntity>("id == $0", book.id).find().firstOrNull()?.let { entity ->
                findLatest(entity)?.let {
                    delete(it)
                }
            }
        }
    }
}