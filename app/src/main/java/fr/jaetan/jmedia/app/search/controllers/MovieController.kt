package fr.jaetan.jmedia.app.search.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.core.realm.entities.toMovies
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.models.works.toBdd

class MovieController: IWorkController<Movie>() {
    override val works = mutableStateListOf<Movie>()
    private val localMovies = mutableListOf<Movie>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(MovieApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localMovies.isNotEmpty()) return

        MainViewModel.movieRepository.all.collect {
            localMovies.clear()
            localMovies.addAll(it.list.toMovies())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        works.replaceAll { manga ->
            manga.copy(isInLibrary = localMovies.find { it.title == manga.title }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Movie) {
        if (localMovies.find { it.title == work.title }.isNull()) {
            MainViewModel.movieRepository.add(work.toBdd())
            return
        }

        localMovies.find { it.title == work.title }?.let {
            MainViewModel.movieRepository.remove(it.toBdd())
        }
    }
}