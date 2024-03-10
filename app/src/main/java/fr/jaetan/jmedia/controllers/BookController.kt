package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.BookApi
import fr.jaetan.jmedia.core.realm.entities.toBooks
import fr.jaetan.jmedia.models.works.Book
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookController : IWorkController<Book>() {
    override val fetchedWorks = mutableStateListOf<Book>()
    override var localWorks = mutableStateListOf<Book>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && fetchedWorks.isNotEmpty()) return

        fetchedWorks.clear()
        fetchedWorks.addAll(BookApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            MainViewModel.bookRepository.all.collect {
                localWorks.clear()
                localWorks.addAll(it.list.toBooks())
                setLibraryValues()
            }
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { book ->
            book.copy(isInLibrary = isInLibrary(book))
        }
    }

    override suspend fun libraryHandler(work: Book) {
        if (work.isInLibrary) {
            localWorks.takeWhereEqualTo(work)?.let {
                MainViewModel.bookRepository.remove(it.toBdd())
            }
            return
        }

        MainViewModel.bookRepository.add(work.toBdd())
    }
}