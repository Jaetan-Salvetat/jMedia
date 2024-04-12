package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.mutableStateListOf
import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.core.realm.entities.toMovies
import fr.jaetan.jmedia.core.realm.repositories.MovieRepository
import fr.jaetan.jmedia.models.works.Movie
import fr.jaetan.jmedia.models.works.takeWhereEqualTo
import fr.jaetan.jmedia.models.works.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieController : IWorkController<Movie>() {
    private val repository by lazy { MovieRepository(MainViewModel.realm) }
    override var localWorks = mutableStateListOf<Movie>()

    override suspend fun fetch(searchValue: String): List<Movie> {
        return MovieApi.search(searchValue)
    }

    override suspend fun initializeFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                localWorks.clear()
                localWorks.addAll(it.list.toMovies())
                setLibraryValues()
            }
        }
    }

    override fun setLibraryValues() {
        /*fetchedWorks.replaceAll { movie ->
            movie.copy(isInLibrary = isInLibrary(movie))
        }*/
    }

    override suspend fun libraryHandler(work: Movie) {
        if (work.isInLibrary) {
            localWorks.takeWhereEqualTo(work)?.let {
                repository.remove(it.toBdd())
            }
            return
        }

        addToLibrary(work)
    }

    private suspend fun addToLibrary(work: Movie) {
        var movie = MovieApi.getDetail(work.apiId)
        movie = movie.copy(id = work.id, title = work.title, isInLibrary = work.isInLibrary)

        /*fetchedWorks.replaceAll {
            if (it.id == work.id) movie
            else it
        }*/

        repository.add(movie.toBdd())
    }

    override suspend fun removeAll() {
        repository.removeAll()
    }
}