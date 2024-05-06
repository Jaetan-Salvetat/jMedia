package fr.jaetan.jmedia.controllers

import fr.jaetan.jmedia.models.medias.IMedia

abstract class IMediaController<T : IMedia> {
    abstract suspend fun fetch(searchValue: String): List<T>
    abstract suspend fun libraryHandler(media: T)
    abstract suspend fun initializeFlow(onDbChanged: (medias: List<T>) -> Unit)
    abstract suspend fun removeAll()
}