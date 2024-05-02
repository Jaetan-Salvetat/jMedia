package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.extensions.log
import fr.jaetan.jmedia.models.ListState
import fr.jaetan.jmedia.models.Sort
import fr.jaetan.jmedia.models.SortDirection
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.WorkType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorksController {
    private val controllersMap = mapOf(
        WorkType.Manga to MangaController(),
        WorkType.Anime to AnimeController(),
        WorkType.Book to BookController(),
        WorkType.Movie to MovieController(),
        WorkType.Serie to SerieController()
    )
    private var lastSearchValue = ""
    private val fetchedMedias = MutableStateFlow(mutableMapOf<WorkType, List<IWork>?>())
    private val localMedias = MutableStateFlow(mutableMapOf<WorkType, List<IWork>?>())
    private val fetchedMediasAsList: List<IWork>
        get() = fetchedMedias.value.values.filterNotNull().flatten()
    val localMediasAsList: List<IWork>
        get() = localMedias.value.values.filterNotNull().flatten()

    var searchState = MutableStateFlow(ListState.Default)

    /**
     * Search medias
     * @param searchValue the field that will be research
     * @param mediaTypes the filters for the research
     */
    suspend fun search(searchValue: String, mediaTypes: List<WorkType>) {
        if (searchValue.length < 2) return
        val force = searchValue != lastSearchValue

        lastSearchValue = searchValue
        searchState.value = ListState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            mediaTypes.forEach { type ->
                "type($type): ${fetchedMedias.value[type]?.size}".log()
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
     * @param filters a list of [WorkType]
     */
    fun getFetchedMedias(sort: Sort, sortDirection: SortDirection, filters: List<WorkType>): List<IWork> {
        val filtered = fetchedMediasAsList.mapNotNull { if (filters.contains(it.type)) it else null }

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
     * Return a [IWorkController] from a [WorkType]
     */
    @Suppress("UNCHECKED_CAST")
    fun getController(type: WorkType): IWorkController<IWork> = controllersMap[type] as IWorkController<IWork>

    /**
     * Initialize all work controllers flow
     */
    suspend fun initializeControllers() {
        controllersMap.forEach {
            it.value.initializeFlow { list ->
                localMedias.value[it.key] = list
            }
        }
    }

    /**
     * Add or remove a media from the db
     * @param media media to save
     */
    suspend fun <T: IWork> libraryHandler(media: T) {
        getController(media.type).libraryHandler(media)
    }

    /**
     * Remove all works from database
     */
    suspend fun removeAll() {
        controllersMap.values.forEach { it.removeAll() }
    }

    companion object {
        val instance = WorksController()
    }
}