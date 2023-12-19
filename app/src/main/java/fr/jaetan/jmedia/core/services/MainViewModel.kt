package fr.jaetan.jmedia.core.services

import fr.jaetan.jmedia.core.services.objectbox.repositories.MangaRepository

object MainViewModel {
    val state = StateViewModel()
    val mangaRepository = MangaRepository()
}