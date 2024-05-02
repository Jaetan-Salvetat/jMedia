package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.core.networking.BookApi
import fr.jaetan.jmedia.core.realm.entities.toBooks
import fr.jaetan.jmedia.core.realm.repositories.BookRepository
import fr.jaetan.jmedia.models.medias.Book
import fr.jaetan.jmedia.models.medias.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookController : IMediaController<Book>() {
    private val repository by lazy { BookRepository(MainViewModel.realm) }

    override suspend fun fetch(searchValue: String): List<Book> {
        return BookApi.search(searchValue)
    }

    override suspend fun initializeFlow(onDbChanged: (medias: List<Book>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                onDbChanged(it.list.toBooks())
            }
        }
    }

    override suspend fun libraryHandler(media: Book) {
        if (media.isInLibrary) {
//            localWorks.takeWhereEqualTo(media)?.let {
//                repository.remove(it.toBdd())
//            }
//            return
        }

        repository.add(media.toBdd())
    }

    override suspend fun removeAll() {
        repository.removeAll()
    }
}