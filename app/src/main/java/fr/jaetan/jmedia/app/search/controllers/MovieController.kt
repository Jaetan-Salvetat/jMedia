package fr.jaetan.jmedia.app.search.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.models.works.Movie

class MovieController: IWorkController<Movie>() {
    override val works = mutableStateListOf<Movie>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(MovieApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() = Unit

    override fun setLibraryValues() = Unit

    override suspend fun libraryHandler(work: Movie) = Unit
}