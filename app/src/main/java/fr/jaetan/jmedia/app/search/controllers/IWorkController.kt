package fr.jaetan.jmedia.app.search.controllers

import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.generateBitmap

abstract class IWorkController<T: IWork> {
    abstract suspend fun fetch(searchValue: String)
    abstract suspend fun libraryHandler(work: T)
    abstract suspend fun initializeFlow()
    protected abstract fun setLibraryValues()

    protected suspend fun generateBitmaps(works: List<T>): List<T> {
        works.forEach { it.image.generateBitmap() }
        return works
    }
}