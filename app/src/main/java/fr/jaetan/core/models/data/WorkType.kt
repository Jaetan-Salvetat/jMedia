package fr.jaetan.core.models.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import fr.jaetan.core.extensions.isNotNull
import fr.jaetan.jmedia.R
import fr.jaetan.jmedia.ui.theme.Green
import fr.jaetan.jmedia.ui.theme.Red

enum class WorkType(@StringRes val textRes: Int, @StringRes val titleRes: Int, private val _backgroundColor: Color) {
    Manga(R.string.mangas, R.string.my_mangas, Red),
    Book(R.string.books, R.string.my_books, Color(0xFFFFAF42)),
    Anime(R.string.animes, R.string.my_animes, Green),
    Serie(R.string.series, R.string.my_series, Color(0xFF37BD35)),
    Movie(R.string.movies, R.string.my_movies, Color(0xFFC9316A));

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

        fun getFromString(name: String?): WorkType? = all.find { it.name == name }

        const val key = "work_type"
    }
}

fun List<WorkType>.toStringList(): String = this.joinToString { it.name }