package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.core.networking.MovieApi
import fr.jaetan.jmedia.core.realm.entities.toMovies
import fr.jaetan.jmedia.core.realm.repositories.MovieRepository
import fr.jaetan.jmedia.models.medias.Movie
import fr.jaetan.jmedia.models.medias.toBdd
import fr.jaetan.jmedia.services.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieController : IMediaController<Movie>() {
    private val repository by lazy { MovieRepository(MainViewModel.realm) }

    override suspend fun fetch(searchValue: String): List<Movie> {
        return MovieApi.search(searchValue)
    }

    override fun initializeFlow(onDbChanged: (medias: List<Movie>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.all.collect {
                onDbChanged(it.list.toMovies())
            }
        }
    }

    override suspend fun libraryHandler(media: Movie) {
        if (media.isInLibrary) {
            repository.remove(media.toBdd())
            return
        }

        addToLibrary(media)
    }

    private suspend fun addToLibrary(media: Movie) {
        var movie = MovieApi.getDetail(media.apiId)
        movie = movie.copy(id = media.id, title = media.title, isInLibrary = media.isInLibrary)

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