package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.models.works.IWork

abstract class IWorkController<T : IWork> {
    abstract suspend fun fetch(searchValue: String): List<T>
    abstract suspend fun libraryHandler(work: T)
    abstract suspend fun initializeFlow(onDbChanged: (medias: List<T>) -> Unit)
    abstract suspend fun removeAll()
}