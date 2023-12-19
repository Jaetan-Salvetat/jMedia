package fr.jaetan.jmedia.core.services.objectbox

import android.content.Context
import io.objectbox.BoxStore


object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun removeAll() {
        store.boxFor(MangaEntity::class.java).removeAll()
        store.boxFor(AuthorEntity::class.java).removeAll()
        store.boxFor(ImageEntity::class.java).removeAll()
        store.boxFor(GenreEntity::class.java).removeAll()
        store.boxFor(DemographicEntity::class.java).removeAll()
    }
}