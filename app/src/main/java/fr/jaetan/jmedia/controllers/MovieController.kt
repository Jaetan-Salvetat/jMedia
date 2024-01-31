package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.core.realm.entities.toMovies
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.models.works.equalTo
import fr.jaetan.jmedia.models.works.toBdd

class MovieController: IWorkController<Movie>() {
    override val works = mutableStateListOf<Movie>()
    override var localWorks = mutableStateListOf<Movie>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && works.isNotEmpty()) return

        works.clear()
        works.addAll(MovieApi.search(searchValue))
        setLibraryValues()
    }

    override suspend fun initializeFlow() {
        if (localWorks.isNotEmpty()) return

        MainViewModel.movieRepository.all.collect {
            localWorks.clear()
            localWorks.addAll(it.list.toMovies())
            setLibraryValues()
        }
    }

    override fun setLibraryValues() {
        works.replaceAll { movie ->
            movie.copy(isInLibrary = localWorks.find { movie.equalTo(it) }.isNotNull())
        }
    }

    override suspend fun libraryHandler(work: Movie) {
        val movie = MovieApi.getDetail(work.apiId)

        if (localWorks.find { it.title == movie.title }.isNull()) {
            MainViewModel.movieRepository.add(movie.toBdd())
            return
        }

        localWorks.find { it.title == movie.title }?.let {
            MainViewModel.movieRepository.remove(it.toBdd())
        }
    }
}