package fr.jaetan.jmedia.controllers

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
        works.replaceAll { movie ->
            movie.copy(isInLibrary = localMovies.find { it.title == movie.title }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Movie) {
        val movie = MovieApi.getDetail(work.apiId)

        if (localMovies.find { it.title == movie.title }.isNull()) {
            MainViewModel.movieRepository.add(movie.toBdd())
            return
        }

        localMovies.find { it.title == movie.title }?.let {
            MainViewModel.movieRepository.remove(it.toBdd())
        }
    }
}