package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.snapshots.SnapshotStateList
import fr.jaetan.jmedia.models.works.IWork

abstract class IWorkController<T: IWork> {
    abstract val fetchedWorks: SnapshotStateList<T>
    abstract val localWorks: SnapshotStateList<T>
    abstract suspend fun fetch(searchValue: String, force: Boolean)
    abstract suspend fun libraryHandler(work: T)
    abstract suspend fun initializeFlow()
    protected abstract fun setLibraryValues()
}