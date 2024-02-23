package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.shared.WorkType

class WorksController {
    val controllersMap = mapOf(
        WorkType.Manga to MangaController(),
        WorkType.Anime to AnimeController(),
        WorkType.Book to BookController(),
        WorkType.Movie to MovieController(),
        WorkType.Serie to SerieController()
    )

    val allAsList: List<IWork>
        get() = controllersMap.map { it.value.localWorks.toList() }.flatten()
    val worksSize: Int
        get() = controllersMap.values.map { it.localWorks.size }.reduce { acc, i -> acc + i }

    @Suppress("UNCHECKED_CAST")
    fun getController(type: WorkType): IWorkController<IWork> = controllersMap[type] as IWorkController<IWork>


    suspend fun initializeControllers() {
        controllersMap.forEach {
            it.value.initializeFlow()
        }
    }
}