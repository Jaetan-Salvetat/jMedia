package fr.jaetan.jmedia.core.realm.repositories

import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow

abstract class IRepository<T: RealmObject> {
    abstract val all: Flow<ResultsChange<T>>

    abstract suspend fun add(media: T)
    abstract suspend fun remove(media: T)

    abstract suspend fun removeAll()
}