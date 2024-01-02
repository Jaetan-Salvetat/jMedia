package fr.jaetan.jmedia.app.search.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.BookApi
import fr.jaetan.jmedia.models.works.Book

class BookController: IWorkController<Book>() {
    override val works = mutableStateListOf<Book>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(BookApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() = Unit

    override fun setLibraryValues() = Unit

    override suspend fun libraryHandler(work: Book) = Unit
}