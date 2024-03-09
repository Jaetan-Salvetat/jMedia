package fr.jaetan.jmedia.controllers

import androidx.compose.runtime.snapshots.SnapshotStateList
import fr.jaetan.jmedia.extensions.isNotNull
import fr.jaetan.jmedia.models.works.IWork
import fr.jaetan.jmedia.models.works.equalTo

abstract class IWorkController<T : IWork> {
    abstract val fetchedWorks: SnapshotStateList<T>
    abstract val localWorks: SnapshotStateList<T>
    abstract suspend fun fetch(searchValue: String, force: Boolean)
    abstract suspend fun libraryHandler(work: T)
    abstract suspend fun initializeFlow()
    protected abstract fun setLibraryValues()
    protected fun isInLibrary(work: T): Boolean = localWorks.find { work.equalTo(it) }.isNotNull()
}