package fr.jaetan.core.services

import android.content.Context
import androidx.lifecycle.ViewModel

object MainViewModel: ViewModel() {
    val state = StateViewModel()

    fun initialize(context: Context) {
        state.initialize(context)
    }
}