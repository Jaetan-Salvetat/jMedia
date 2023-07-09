package fr.jaetan.core.models.data.works

import androidx.annotation.StringRes
import fr.jaetan.jmedia.R

enum class WorkState(@StringRes val nameRes: Int) {
    InProgress(R.string.in_progress),
    Finished(R.string.finished),
    // Cancelled(R.string.canceled),
    // Paused(R.string.paused),
    Unknown(R.string.unknown_author);

    companion object {
        fun fromString(workStateString: String?): WorkState = when {
            workStateString?.contains("En cours") == true -> InProgress
            workStateString?.contains("Terminé") == true -> Finished
            else -> Unknown
        }
    }
}