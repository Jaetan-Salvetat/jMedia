package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.BookApi
import fr.jaetan.jmedia.core.realm.entities.toBooks
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.works.Book
import fr.jaetan.jmedia.models.works.toBdd

class BookController: IWorkController<Book>() {
    override val works = mutableStateListOf<Book>()
    private val localBooks = mutableListOf<Book>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(BookApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localBooks.isNotEmpty()) return

        MainViewModel.bookRepository.all.collect {
            localBooks.clear()
            localBooks.addAll(it.list.toBooks())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        works.replaceAll { manga ->
            manga.copy(isInLibrary = localBooks.find { it.title == manga.title }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Book) {
        if (localBooks.find { it.title == work.title }.isNull()) {
            MainViewModel.bookRepository.add(work.toBdd())
            return
        }

        localBooks.find { it.title == work.title }?.let {
            MainViewModel.bookRepository.remove(it.toBdd())
        }
    }
}