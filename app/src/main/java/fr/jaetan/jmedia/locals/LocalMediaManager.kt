package fr.jaetan.jmedia.locals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import fr.jaetan.jmedia.controllers.WorksController
import fr.jaetan.jmedia.models.ReplaceableLocal

@Composable
private fun rememberMediaManager() = remember {
    WorksController.instance
}

object LocalMediaManager : ReplaceableLocal<WorksController>() {
    @Composable
    override fun currentValue(): WorksController = rememberMediaManager()
}