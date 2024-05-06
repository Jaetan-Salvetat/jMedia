package fr.jaetan.jmedia.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jaetan.jmedia.extensions.isNull
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection
import fr.jaetan.jmedia.models.medias.IMedia
import fr.jaetan.jmedia.models.medias.equalTo
import fr.jaetan.jmedia.models.medias.shared.MediaType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MediasManager : ViewModel() {
    private val controllersMap = mapOf(
        MediaType.Manga to MangaController(),
        MediaType.Anime to AnimeController(),
        MediaType.Book to BookController(),
        MediaType.Movie to MovieController(),
        MediaType.Serie to SerieController()
    )
    private var lastSearchValue = ""
    private val fetchedMedias = MutableStateFlow(mutableMapOf<MediaType, List<IMedia>?>())
    val count: Int
        get() = localMediasAsList.size
    private val localMedias = MutableStateFlow(mutableMapOf<MediaType, List<IMedia>?>())
    private val fetchedMediasAsList: List<IMedia>
        get() = fetchedMedias.value.values.filterNotNull().flatten()
    val localMediasAsList: List<IMedia>
        get() = localMedias.value.values.filterNotNull().flatten()

    var searchState = MutableStateFlow(ListState.Default)

    init {
        viewModelScope.launch {
            controllersMap.forEach {
                it.value.initializeFlow { list ->
                    localMedias.value[it.key] = list
                }
            }
        }
    }

    /**
     * Search medias
     * @param searchValue the field that will be research
     * @param mediaTypes the filters for the research
     */
    suspend fun search(searchValue: String, mediaTypes: List<MediaType>) {
        if (searchValue.length < 2) return
        lastSearchValue = searchValue
        searchState.value = ListState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            mediaTypes.forEach { type ->
                val force = searchValue != lastSearchValue
                        || fetchedMedias.value[type].isNull()

                if (fetchedMedias.value[type] != null && !force) return@forEach

                controllersMap[type]?.fetch(searchValue)?.let {
                    fetchedMedias.value[type] = it
                }
            }

            searchState.value = when {
                fetchedMediasAsList.isNotEmpty() -> ListState.HasData
                else -> ListState.EmptyData
            }
        }
    }

    /**
     * Return all medias filtered
     * @param sort [Name, Rating or Default]
     * @param sortDirection [Ascending, Descending]
     * @param filters a list of [MediaType]
     */
    fun getFetchedMedias(sort: Sort, sortDirection: SortDirection, filters: List<MediaType>): List<IMedia> {
        val filtered = fetchedMediasAsList.filter { filters.contains(it.type) }

        return if (sortDirection == SortDirection.Ascending) {
            when (sort) {
                Sort.Name -> filtered.sortedBy { it.title }
                Sort.Rating -> filtered.sortedBy { it.rating }
                Sort.Default -> filtered
            }
        } else {
            when (sort) {
                Sort.Name -> filtered.sortedByDescending { it.title }
                Sort.Rating -> filtered.sortedByDescending { it.rating }
                Sort.Default -> filtered
            }
        }
    }

    /**
     * Add or remove a media from the db
     * @param media media to save
     */
    fun <T : IMedia> libraryHandler(media: T) {
        CoroutineScope(Dispatchers.IO).launch {
            val newMedia = localMedias.value[media.type]?.find { it.equalTo(media) }
                ?: media
            getController(media.type).libraryHandler(newMedia)
        }
    }

    /**
     * Remove all works from database
     */
    suspend fun removeAll() {
        controllersMap.values.forEach { it.removeAll() }
    }

    /**
     * Return a [IMediaController] from a [MediaType]
     */
    @Suppress("UNCHECKED_CAST")
    private fun getController(type: MediaType): IMediaController<IMedia> = controllersMap[type] as IMediaController<IMedia>

    companion object {
        val instance = MediasManager()
    }
}