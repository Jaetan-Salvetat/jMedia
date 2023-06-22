package fr.jaetan.core.services

import android.content.Context
import fr.jaetan.core.repositories.MangaRepository

object MainViewModel {
    val state = StateViewModel()
    val mangaRepository = MangaRepository()

    fun initialize(context: Context) {
        state.initialize(context)
    }
}