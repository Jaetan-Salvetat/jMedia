package fr.jaetan.jmedia.app.search.controllers

import androidx.compose.runtime.snapshots.SnapshotStateList
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.generateBitmap

abstract class IWorkController<T: IWork> {
    abstract val works: SnapshotStateList<T>
    abstract suspend fun fetch(searchValue: String, force: Boolean)
    abstract suspend fun libraryHandler(work: T)
    abstract suspend fun initializeFlow()
    protected abstract fun setLibraryValues()

    protected suspend fun generateBitmaps(works: List<T>): List<T> {
        works.forEach { it.image.generateBitmap() }
        return works
    }
}