package fr.jaetan.jmedia.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import fr.jaetan.jmedia.controllers.MediasManager
import fr.jaetan.jmedia.models.ReplaceableLocal

@Composable
private fun rememberMediaManager() = remember {
    MediasManager.instance
}

object LocalMediaManager : ReplaceableLocal<MediasManager>() {
    @Composable
    override fun currentValue(): MediasManager = rememberMediaManager()
}