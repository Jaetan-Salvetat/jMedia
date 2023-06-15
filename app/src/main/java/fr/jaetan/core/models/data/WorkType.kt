package fr.jaetan.core.models.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import fr.jaetan.core.extensions.isNotNull
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.ui.theme.Green
import fr.jaetan.jmedia.ui.theme.Red

enum class WorkType(@StringRes val textRes: Int, private val _backgroundColor: Color) {
    Manga(R.string.mangas, Red),
    Book(R.string.books, Color(0xFFFFAF42)),
    Anime(R.string.animes, Green),
    Serie(R.string.series, Color(0xFF37BD35)),
    Movie(R.string.movies, Color(0xFFC9316A));

    fun getBackgroundColor(isDarkTheme: Boolean): Color {
        if (isDarkTheme) return _backgroundColor.copy(alpha = .7f)
        return _backgroundColor
    }

    companion object {
        val all: List<WorkType> = WorkType.values().toList()

        fun toList(values: String?): List<WorkType>? {
            if (values?.isBlank() == true) return emptyList()
            if (values.isNotNull()) {
                return values!!.split(", ").map { getFromString(it) }.requireNoNulls()
            }
            return null
        }

        private fun getFromString(name: String): WorkType? = all.find { it.name == name }
    }
}

fun List<WorkType>.toStringList(): String = this.joinToString { it.name }