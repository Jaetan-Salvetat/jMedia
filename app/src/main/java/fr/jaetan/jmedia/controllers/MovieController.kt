package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.core.realm.entities.toMovies
import fr.jaetan.jmedia.core.services.MainViewModel
import fr.jaetan.jmedia.extensions.printDataClassToString
import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd

class MovieController: IWorkController<Movie>() {
    override val fetchedWorks = mutableStateListOf<Movie>()
    override var localWorks = mutableStateListOf<Movie>()

    override suspend fun fetch(searchValue: String, force: Boolean) {
        if (!force && fetchedWorks.isNotEmpty()) return

        fetchedWorks.clear()
        fetchedWorks.addAll(MovieApi.search(searchValue))
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
        fetchedWorks.replaceAll { movie ->
            movie.copy(isInLibrary = isInLibrary(movie))
        }
    }

    override suspend fun libraryHandler(work: Movie) {
        val movie = MovieApi.getDetail(work.apiId)

        if (!work.isInLibrary) {
            work.printDataClassToString("testt:work")
            movie.printDataClassToString("testt:movie")
            MainViewModel.movieRepository.add(movie.toBdd())
            return
        }

        localWorks.takeWhereEqualTo(movie)?.let {
            MainViewModel.movieRepository.remove(it.toBdd())
        }
    }
}