package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.BookApi
import fr.jaetan.jmedia.core.realm.entities.toBooks
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.models.works.Book
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd

class BookController: IWorkController<Book>() {
    override val fetchedWorks = mutableStateListOf<Book>()
    override var localWorks = mutableStateListOf<Book>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && fetchedWorks.isNotEmpty()) return

        fetchedWorks.clear()
        fetchedWorks.addAll(BookApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localWorks.isNotEmpty()) return

        MainViewModel.bookRepository.all.collect {
            localWorks.clear()
            localWorks.addAll(it.list.toBooks())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        fetchedWorks.replaceAll { book ->
            book.copy(isInLibrary = isInLibrary(book))
        }
    }

    override suspend fun libraryHandler(work: Book) {
        if (!work.isInLibrary) {
            MainViewModel.bookRepository.add(work.toBdd())
            return
        }

        localWorks.takeWhereEqualTo(work)?.let {
            MainViewModel.bookRepository.remove(it.toBdd())
        }
    }
}